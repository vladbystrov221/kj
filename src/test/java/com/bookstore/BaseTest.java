package com.bookstore;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
 
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public abstract class BaseTest {
    // Common test configurations and utilities can be added here
} 