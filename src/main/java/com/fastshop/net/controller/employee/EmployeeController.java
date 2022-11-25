package com.fastshop.net.controller.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.model.Notify;
import com.fastshop.net.model.Role;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service.NotifyService;
import com.fastshop.net.service.ProductService;
import com.fastshop.net.service.RoleService;
import com.fastshop.net.service._CookieService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmployeeController {
    @Autowired
    _CookieService cookie;
    @Autowired
    AccountService accountService;
    @Autowired
    RoleService roleService;
    @Autowired
    ProductService productService;
    @Autowired
    NotifyService notifyService;
    @Autowired
    AuthorityService authorityService;


    @RequestMapping("/account/add")
    public String add(Account account) {
        // save account
        accountService.save(account);

        // save auth
        Role role = roleService.findById("STAFF");
        Authority authority = new Authority();
        authority.setAccount(account);
        authority.setRole(role);
        authorityService.save(authority);
        return "redirect:/admin/employee";
    }


    @RequestMapping("/account/active/{username}")
    public String delete(@PathVariable("username") String username) {
        Account account = accountService.findById(username);
        account.setActive( account.getActive() != null ? false : null);
        accountService.save(account);
        return "redirect:/admin/employee";
    }


    @RequestMapping("/account/search")
    public String name(Model model, String keyword) {
        if (keyword.equalsIgnoreCase("") || keyword == null) {
            return "redirect:/admin/employee";
        }
        else {
            model.addAttribute("staff", new Account());
            model.addAttribute("title_main", "Admin - Thống kê danh sách nhân viên");
            model.addAttribute("employees", authorityService.findByKeyword(keyword));
            model.addAttribute("page", "admin.employee");
            return "index";
        }
    }

    @RequestMapping("/notify/add")
    public String sentPDF(Model model, @ModelAttribute("notify") Notify notify, @ModelAttribute("auth") Authority auth) {
        try {
            return "redirect:/staff/report";
        } catch (Exception e) {
            return "redirect:/login.fastshop.com";
        }
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
