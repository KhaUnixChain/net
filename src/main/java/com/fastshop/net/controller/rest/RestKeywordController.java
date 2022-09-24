package com.fastshop.net.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Keyword;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.KeywordService;

@CrossOrigin("*")
@RestController
public class RestKeywordController {
    @Autowired
    KeywordService keywordService;
    @Autowired
    AccountService accountService;
    
    @GetMapping("/rest/keywords")
    public List<Keyword> findAll() {
        return keywordService.findAll();
    }

    @GetMapping("/rest/keywords/account/{username}")
    public List<Keyword> findByAccountID(@PathVariable("username") String username) {
        Account account = accountService.findById(username);
        return keywordService.findByAccount(account);
    }

    @GetMapping("/rest/keywords/lastid/{username}")
    public Integer getIDClass(@PathVariable("username") String username) {
        Account account = accountService.findById(username);
        List<Keyword> number = keywordService.findByAccount(account);
        return number.get(number.size() - 1).getId();
    }

    @GetMapping("/rest/keywords/{id}")
    public Keyword findById(@PathVariable("id") int id) {
        return keywordService.findById(id);
    }

    @PostMapping("/rest/keywords")
    public Keyword add(@RequestBody Keyword keyword) {
        keywordService.save(keyword);
        return keyword;
    }

    @DeleteMapping("/rest/keywords/{id}")
    public void deleteKeyword(@PathVariable("id") Integer id) {
        keywordService.deleteById(id);
    }
}
