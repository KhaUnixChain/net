package com.fastshop.net.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Notify;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.NotifyService;

@RestController
public class RestNotifyController {
    @Autowired
    NotifyService notifyService;
    @Autowired
    AccountService accountService;

    @GetMapping("/rest/notifications/{username}")
    public List<Notify> findAll(@PathVariable("username") String username) {
        Account account = accountService.findByUsername(username);
        return notifyService.findAllByAccAndNowAndStatusOrderBy(account);
    }
}
