package com.bookstore.config;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0) {
            Book book1 = new Book();
            book1.setTitle("Spring Boot in Action");
            book1.setAuthor("Craig Walls");
            book1.setDescription("Learn Spring Boot development");
            book1.setPrice(29.99);
            bookRepository.save(book1);

            Book book2 = new Book();
            book2.setTitle("Java: The Complete Reference");
            book2.setAuthor("Herbert Schildt");
            book2.setDescription("Comprehensive Java guide");
            book2.setPrice(39.99);
            bookRepository.save(book2);

            Book book3 = new Book();
            book3.setTitle("Clean Code");
            book3.setAuthor("Robert C. Martin");
            book3.setDescription("Writing maintainable code");
            book3.setPrice(34.99);
            bookRepository.save(book3);

            Book book4 = new Book();
            book4.setTitle("Effective Java");
            book4.setAuthor("Joshua Bloch");
            book4.setDescription("Best practices for Java programming");
            book4.setPrice(45.99);
            bookRepository.save(book4);

            Book book5 = new Book();
            book5.setTitle("Spring in Action");
            book5.setAuthor("Craig Walls");
            book5.setDescription("Complete guide to Spring Framework");
            book5.setPrice(39.99);
            bookRepository.save(book5);
        }
    }
} 