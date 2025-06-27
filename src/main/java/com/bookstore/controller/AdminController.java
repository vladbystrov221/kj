package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.entity.User;
import com.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final BookService bookService;

    // Главная страница админки
    @GetMapping
    public String adminPanel(
            @RequestParam(value = "search", required = false) String search,
            Model model) {
        
        if (search != null && !search.isEmpty()) {
            model.addAttribute("books", bookService.searchBooks(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("books", bookService.getAllBooks());
        }
        
        return "admin/dashboard";
    }

    // Форма добавления книги
    @GetMapping("/books/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "admin/book-form";
    }

    // Форма редактирования книги
    @GetMapping("/books/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isEmpty()) {
            logger.warn("Попытка редактирования несуществующей книги с ID: {}", id);
            redirectAttributes.addFlashAttribute("error", "Книга с ID " + id + " не найдена");
            return "redirect:/admin";
        }
        
        model.addAttribute("book", book.get());
        return "admin/book-form";
    }

    // Сохранение книги (создание или обновление)
    @PostMapping("/books/save")
    public String saveBook(
            @ModelAttribute Book book,
            @AuthenticationPrincipal User currentUser,
            RedirectAttributes redirectAttributes) {
        try {
            boolean isNew = book.getId() == null;
            
            // Устанавливаем временные метки
            if (isNew) {
                book.setCreatedAt(LocalDateTime.now());
                logger.info("Создание новой книги: {}", book.getTitle());
            } else {
                // Сохраняем оригинальную дату создания
                Optional<Book> existingBook = bookService.getBookById(book.getId());
                if (existingBook.isPresent()) {
                    book.setCreatedAt(existingBook.get().getCreatedAt());
                    logger.info("Обновление книги с ID {}: {}", book.getId(), book.getTitle());
                }
            }
            
            book.setUpdatedAt(LocalDateTime.now());
            
            // Сохраняем книгу
            Book savedBook = bookService.saveBook(book);
            
            redirectAttributes.addFlashAttribute("message", 
                "Книга \"" + savedBook.getTitle() + "\" успешно сохранена");
        } catch (Exception e) {
            logger.error("Ошибка при сохранении книги: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", 
                "Ошибка при сохранении книги: " + e.getMessage());
        }
        
        return "redirect:/admin";
    }

    // Удаление книги
    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Book> book = bookService.getBookById(id);
            if (book.isEmpty()) {
                logger.warn("Попытка удаления несуществующей книги с ID: {}", id);
                redirectAttributes.addFlashAttribute("error", "Книга с ID " + id + " не найдена");
                return "redirect:/admin";
            }
            
            String title = book.get().getTitle();
            bookService.deleteBook(id);
            logger.info("Книга удалена: {} (ID: {})", title, id);
            
            redirectAttributes.addFlashAttribute("message", "Книга \"" + title + "\" успешно удалена");
        } catch (Exception e) {
            logger.error("Ошибка при удалении книги с ID {}: {}", id, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении книги: " + e.getMessage());
        }
        
        return "redirect:/admin";
    }
} 