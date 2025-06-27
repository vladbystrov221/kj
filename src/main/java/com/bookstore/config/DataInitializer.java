package com.bookstore.config;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final BookRepository bookRepository;
    private final UserService userService;

    @Override
    @Transactional
    public void run(String... args) {
        logger.info("Инициализация данных...");
        
        // Создаем администратора
        userService.createAdminIfNotExists();
        
        // Добавляем книги только если репозиторий пуст
        if (bookRepository.count() == 0) {
            logger.info("Добавление русских книг в каталог...");
            List<Book> books = Arrays.asList(
                createBook("Война и мир", "Лев Толстой", "Эпический роман, описывающий события войны 1812 года и жизнь русского общества начала XIX века.", "978-5-389-12345-6", new BigDecimal("799.00"), "Эксмо", 1869, 1225, "Русский"),
                createBook("Преступление и наказание", "Фёдор Достоевский", "Психологический роман о моральных и философских дилеммах бывшего студента, совершившего убийство.", "978-5-389-12346-3", new BigDecimal("599.00"), "АСТ", 1866, 672, "Русский"),
                createBook("Мастер и Маргарита", "Михаил Булгаков", "Роман о визите дьявола в атеистическую Москву 1930-х годов, переплетенный с историей Понтия Пилата.", "978-5-389-12347-0", new BigDecimal("649.00"), "Азбука", 1967, 480, "Русский"),
                createBook("Евгений Онегин", "Александр Пушкин", "Роман в стихах, считающийся эталоном русской поэзии и литературы.", "978-5-389-12348-7", new BigDecimal("450.00"), "Детская литература", 1833, 224, "Русский"),
                createBook("Тихий Дон", "Михаил Шолохов", "Эпопея о донском казачестве в годы Первой мировой и Гражданской войн.", "978-5-389-12349-4", new BigDecimal("899.00"), "Вече", 1940, 1280, "Русский"),
                createBook("Анна Каренина", "Лев Толстой", "Роман о трагической любви замужней женщины из высшего общества к офицеру.", "978-5-389-12350-0", new BigDecimal("750.00"), "Эксмо", 1877, 864, "Русский"),
                createBook("Идиот", "Фёдор Достоевский", "Роман о добром и чистом душой князе, который сталкивается с жестокостью и цинизмом общества.", "978-5-389-12351-7", new BigDecimal("580.00"), "АСТ", 1869, 640, "Русский"),
                createBook("Герой нашего времени", "Михаил Лермонтов", "Первый психологический роман в русской литературе, рассказывающий о судьбе поколения 1830-х годов.", "978-5-389-12352-4", new BigDecimal("420.00"), "Азбука", 1840, 288, "Русский")
            );
            
            bookRepository.saveAll(books);
            logger.info("Добавлено {} книг в каталог", books.size());
        } else {
            logger.info("Каталог книг уже содержит {} записей", bookRepository.count());
        }
        
        logger.info("Инициализация данных завершена");
    }
    
    private Book createBook(String title, String author, String description, String isbn, BigDecimal price, 
                           String publisher, Integer publicationYear, Integer pages, String language) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        book.setIsbn(isbn);
        book.setPrice(price);
        book.setPublisher(publisher);
        book.setPublicationYear(publicationYear);
        book.setPages(pages);
        book.setLanguage(language);
        book.setCreatedAt(LocalDateTime.now());
        return book;
    }
} 