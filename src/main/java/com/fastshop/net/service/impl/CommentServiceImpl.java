package com.fastshop.net.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Comment;
import com.fastshop.net.model.Product;
import com.fastshop.net.repository.CommentDAO;
import com.fastshop.net.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentDAO commentDAO;

    @Override
    public Page<Comment> getCommentsByProduct(Product product, Pageable pageable) {
        return commentDAO.findByProduct(product, pageable);
    }

    @Override
    public Page<Comment> getCommentsByAccount(Account account, Pageable pageable) {
        return commentDAO.findByAccount(account, pageable);
    }

    @Override
    public Long countByProduct(Product product) {
        return commentDAO.countByProduct(product);
    }
    
}
