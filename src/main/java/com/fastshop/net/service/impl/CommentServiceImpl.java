package com.fastshop.net.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<Integer, List<Integer>> getRateByProduct(Product product) {
        List<Comment> comments = product.getComments();
        Map<Integer, List<Integer>> rates = new HashMap<>();
        for (Comment comment : comments) {
            List<Integer> number = new ArrayList<>();
            int rate = comment.getRate();
            if (rate == 5) {
                for (int i = 0; i < 5; i++) {
                    number.add(1);
                }
            }
            else {
                for (int i = 0; i < rate; i++) {
                    number.add(1);
                }

                for (int i = 0; i < 5 - rate; i++) {
                    number.add(0);
                }
            }

            rates.put(comment.getId(), number);
        }
        return rates;
    }

    @Override
    public List<Integer> getMaxStar(Product product) {
        int max = commentDAO.getMaxStar(product);
        List<Integer> number = new ArrayList<>();
        if (max == 5) {
            for (int i = 0; i < 5; i++) {
                number.add(1);
            }
        }
        else {
            for (int i = 0; i < max; i++) {
                number.add(1);
            }

            for (int i = 0; i < 5 - max; i++) {
                number.add(0);
            }
        }

        return number;
    }

}
