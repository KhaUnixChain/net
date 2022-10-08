package com.fastshop.net.controller.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.model.Product;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service.CategoryService;
import com.fastshop.net.service.ProductService;
import com.fastshop.net.service._CookieService;
import com.fastshop.net.service._CopyImage;

@Controller
public class ProductController {
    @Autowired
    _CopyImage copyImage;
    @Autowired
    _CookieService cookie;
    @Autowired
    _CookieService cookieService;
    @Autowired
    ProductService productSevice;
    @Autowired
    AccountService accountService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    AuthorityService authorityService;

    /**
     * this is add a product in database
     * @return 
     * */
    @RequestMapping("/product/adđ")
    public String add(Model model, @ModelAttribute("products") Product product) {
        String path = new ClassPathResource("/dist/img/products/").getPath();
        System.out.println(path);

        productSevice.save(product);
        model.addAttribute("page", "admin.product");
        model.addAttribute("product", new Product());
        model.addAttribute("title_main", "Admin - Thống kê danh sách sản phẩm");
        model.addAttribute("categories", categoryService.findAll());
        return "index";
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
