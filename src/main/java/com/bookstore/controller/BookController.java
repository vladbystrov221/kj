package com.bookstore.controller;

import com.bookstore.dto.BookDto;
import com.bookstore.entity.Book;
import com.bookstore.entity.Favorite;
import com.bookstore.entity.User;
import com.bookstore.service.BookService;
import com.bookstore.service.FavoriteService;
import com.bookstore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final UserService userService;
    private final FavoriteService favoriteService;

    @GetMapping("/")
    public String listBooks(@RequestParam(required = false) String search, Model model) {
        List<Book> books;
        if (search != null && !search.trim().isEmpty()) {
            books = bookService.searchBooks(search);
        } else {
            books = bookService.getAllBooks();
        }
        
        // Проверяем, какие книги в избранном у текущего пользователя
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            User user = (User) userService.loadUserByUsername(auth.getName());
            List<Long> favoriteBookIds = favoriteService.getFavoriteBookIdsByUserId(user.getId());
            
            // Помечаем книги, которые находятся в избранном
            books.forEach(book -> book.setInFavorites(favoriteBookIds.contains(book.getId())));
        }
        
        model.addAttribute("books", books);
        model.addAttribute("search", search);
        return "index";
    }
    
    @GetMapping("/books/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        Optional<Book> bookOpt = bookService.getBookById(id);
        if (bookOpt.isEmpty()) {
            return "redirect:/?error=Книга не найдена";
        }
        
        Book book = bookOpt.get();
        
        // Проверяем, в избранном ли книга у текущего пользователя
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            User user = (User) userService.loadUserByUsername(auth.getName());
            boolean isInFavorites = favoriteService.isBookInFavorites(user.getId(), book.getId());
            book.setInFavorites(isInFavorites);
        }
        
        model.addAttribute("book", book);
        return "book-details";
    }
    
    @GetMapping("/favorites")
    public String viewFavorites(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/login";
        }
        
        User user = (User) userService.loadUserByUsername(auth.getName());
        List<Favorite> favorites = favoriteService.getFavoritesByUserId(user.getId());
        
        // Помечаем все книги как избранные
        favorites.forEach(favorite -> favorite.getBook().setInFavorites(true));
        
        model.addAttribute("favorites", favorites);
        return "favorites";
    }
    
    @PostMapping("/favorites/toggle")
    public String toggleFavorite(
            @RequestParam Long bookId,
            @RequestParam(required = false) String returnUrl,
            RedirectAttributes redirectAttributes) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/login";
        }
        
        User user = (User) userService.loadUserByUsername(auth.getName());
        boolean isInFavorites = favoriteService.isBookInFavorites(user.getId(), bookId);
        
        if (isInFavorites) {
            favoriteService.removeFromFavorites(user.getId(), bookId);
            redirectAttributes.addFlashAttribute("message", "Книга удалена из избранного");
        } else {
            favoriteService.addToFavorites(user.getId(), bookId);
            redirectAttributes.addFlashAttribute("message", "Книга добавлена в избранное");
        }
        
        // Возвращаемся на страницу, с которой пришел запрос
        if (returnUrl != null && !returnUrl.isEmpty()) {
            return "redirect:" + returnUrl;
        }
        
        return "redirect:/";
    }
} 