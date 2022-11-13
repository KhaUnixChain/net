package com.fastshop.net.controller.rest;

import org.springframework.web.bind.annotation.RestController;
import com.fastshop.net.model.Account;
import com.fastshop.net.model.Comment;
import com.fastshop.net.service.CommentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
public class RestCommentController {
    @Autowired
     CommentService commentService;

    @GetMapping("/rest/comments")
    public List<Comment> get(Model model) {
        return commentService.findAll();
    }

    @GetMapping("/rest/comments/{id}")
    public Comment getById(@PathVariable("id") Integer id) {
        return commentService.findById(id);
    }
    @GetMapping("/rest/comments/accounts/{account}")
    public List<Comment> getCommentByAccount(@PathVariable("account") Account account) {
        return commentService.findByAccount(account);
    }
    @PostMapping("/rest/comments")
    public Comment post(@RequestBody Comment comment) {
        commentService.save(comment);
        return comment;
    }

    @PutMapping("/rest/comments/{id}")
    public Comment put(@PathVariable("id") Integer id, @RequestBody Comment comment) {
        if (commentService.findAll().contains(comment)) {
            commentService.save(comment);
        }
        return comment;
    }
    
    @DeleteMapping("/rest/comments/{id}")
    public void delete(@PathVariable("id") Integer id) {
        commentService.deleteById(id);
    }
}
