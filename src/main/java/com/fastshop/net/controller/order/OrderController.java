package com.fastshop.net.controller.order;

import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
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

    @RequestMapping("/staff/status")
    public String status(Model model, @RequestParam("kind") Integer kindOf) {
        Date toNow = java.sql.Date.valueOf(LocalDate.now());
        List<Order> list = (kindOf == 100) ? orderService.getAllOfOrderToday(new Date()): orderService.findByStatus(kindOf);
        model.addAttribute("page", "staff.home");
        model.addAttribute("ordertoday", list);
        model.addAttribute("orderNotToday", orderService.findNotByCreateDate(toNow));
        model.addAttribute("title_main", "Trang chủ quản lý hóa đơn hàng ngày ");
        return "redirect:/staff/home";
    }


    /**
     * this is model of authority
     * @return
     */
    @ModelAttribute("auth")
    public Authority getAuth() {
        Authority auth = null;
        String username = cookie.getValue("username");
        if (username != null) {
            Account account = accountService.findByUsernameOrEmail(username, username);
            auth = authorityService.findByAccount(account);
        }
        return auth;
    }
}
