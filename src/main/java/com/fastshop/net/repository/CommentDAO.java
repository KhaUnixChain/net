package com.fastshop.net.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Comment;
import com.fastshop.net.model.Product;

@Repository
public interface CommentDAO extends JpaRepository<Comment, Integer>{
    Page<Comment> findByProduct(Product product, Pageable pageable);
    Page<Comment> findByAccount(Account account, Pageable pageable);
    Long countByProduct(Product product);
}
