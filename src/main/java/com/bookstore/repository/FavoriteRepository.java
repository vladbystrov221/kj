package com.bookstore.repository;

import com.bookstore.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    
    List<Favorite> findByUserId(Long userId);
    
    boolean existsByUserIdAndBookId(Long userId, Long bookId);
    
    @Transactional
    void deleteByUserIdAndBookId(Long userId, Long bookId);
    
    @Transactional
    void deleteByUserId(Long userId);
    
    @Transactional
    void deleteByBookId(Long bookId);
} 