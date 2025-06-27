package com.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BookstoreApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(BookstoreApplication.class);

    public static void main(String[] args) {
        logger.info("Запуск приложения Bookstore");
        ConfigurableApplicationContext context = SpringApplication.run(BookstoreApplication.class, args);
        logger.info("Приложение Bookstore успешно запущено");
    }
} 