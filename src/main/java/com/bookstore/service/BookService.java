package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
    
    public List<Book> searchBooks(String search) {
        if (search == null || search.trim().isEmpty()) {
            return getAllBooks();
        }
        
        String searchTerm = search.trim();
        logger.info("Поиск книг по запросу: {}", searchTerm);
        
        return bookRepository.searchBooks(searchTerm);
    }
    
    public Page<Book> searchBooks(String search, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(search, search, pageable);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        boolean isNew = book.getId() == null;
        Book savedBook = bookRepository.save(book);
        
        if (isNew) {
            logger.info("Создана новая книга: {} (ID: {})", book.getTitle(), savedBook.getId());
        } else {
            logger.info("Обновлена книга: {} (ID: {})", book.getTitle(), book.getId());
        }
        
        return savedBook;
    }

    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
        bookRepository.deleteById(id);
            logger.info("Удалена книга с ID: {}", id);
        } else {
            logger.warn("Попытка удалить несуществующую книгу с ID: {}", id);
        }
    }
}
