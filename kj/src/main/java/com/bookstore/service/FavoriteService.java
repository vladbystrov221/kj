package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.Favorite;
import com.bookstore.entity.User;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.FavoriteRepository;
import com.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {
    
    private static final Logger logger = LoggerFactory.getLogger(FavoriteService.class);
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    
    public List<Favorite> getFavoritesByUserId(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }
    
    public List<Long> getFavoriteBookIdsByUserId(Long userId) {
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        return favorites.stream()
                .map(favorite -> favorite.getBook().getId())
                .collect(Collectors.toList());
    }
    
    public boolean isBookInFavorites(Long userId, Long bookId) {
        return favoriteRepository.existsByUserIdAndBookId(userId, bookId);
    }
    
    public void addToFavorites(Long userId, Long bookId) {
        // Проверяем, не добавлена ли уже книга в избранное
        if (favoriteRepository.existsByUserIdAndBookId(userId, bookId)) {
            logger.info("Книга с id {} уже в избранном у пользователя с id {}", bookId, userId);
            return;
        }
        
        // Получаем пользователя и книгу
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        
        if (userOpt.isEmpty() || bookOpt.isEmpty()) {
            logger.error("Не удалось добавить книгу в избранное: пользователь или книга не найдены");
            return;
        }
        
        // Создаем и сохраняем новую запись в избранном
        Favorite favorite = new Favorite(userOpt.get(), bookOpt.get());
        favoriteRepository.save(favorite);
        
        logger.info("Книга с id {} добавлена в избранное пользователя с id {}", bookId, userId);
    }
    
    public void removeFromFavorites(Long userId, Long bookId) {
        favoriteRepository.deleteByUserIdAndBookId(userId, bookId);
        logger.info("Книга с id {} удалена из избранного пользователя с id {}", bookId, userId);
    }
    
    public void removeAllFavorites(Long userId) {
        favoriteRepository.deleteByUserId(userId);
        logger.info("Все избранные книги удалены у пользователя с id {}", userId);
    }
} 