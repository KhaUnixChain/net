package com.fastshop.net.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fastshop.net.model.Status;
import com.fastshop.net.service.StatusService;

public class RestStatusController {
    @Autowired
    StatusService statusService;
    
    @GetMapping("/rest/status/{id}")
    public Status getById(@PathVariable("id") Integer id) {
        return statusService.findById(id);
    }
}
