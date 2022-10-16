package com.fastshop.net.controller.order;

import org.springframework.stereotype.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fastshop.net.model.Order;
import com.fastshop.net.model.Status;

import com.fastshop.net.service.ProductService;
import com.fastshop.net.service.StatusService;
import com.fastshop.net.service._CookieService;
import com.fastshop.net.service._GetListFile;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service.CategoryDetailService;
import com.fastshop.net.service.CategoryService;
import com.fastshop.net.service.OrderService;
import com.fastshop.net.service.ProductDetailService;

@Controller
public class OrderController {
    @Autowired
    _CookieService cookie;
    @Autowired
    _GetListFile getListFile;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    AccountService accountService;
    @Autowired
    ProductService productSevice;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryDetailService categoryDetailService;
    @Autowired
    ProductDetailService productDetailService;
    @Autowired
    OrderService orderService;
    @Autowired
    StatusService statusService;


    @RequestMapping("/staff/confirm/{id}")
    public String confirm(@RequestParam("status") Integer kind, @PathVariable("id") Long orderId) {
        try {
            Order order = orderService.findById(orderId);
            Status status = statusService.findById(kind);
            order.setStatus(status);
            orderService.save(order);
            return "redirect:/staff/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login.fastshop.com";
        }
    }
}
