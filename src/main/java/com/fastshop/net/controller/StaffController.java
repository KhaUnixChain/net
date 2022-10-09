package com.fastshop.net.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.model.Product;
import com.fastshop.net.service.ProductService;
import com.fastshop.net.service._CookieService;
import com.fastshop.net.service._GetListFile;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service.CategoryDetailService;
import com.fastshop.net.service.CategoryService;
import com.fastshop.net.service.OrderService;
import com.fastshop.net.service.ProductDetailService;

@Controller
public class StaffController {
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
    
    @RequestMapping("/staff/home")
    public String home(Model model, @ModelAttribute("auth") Authority auth) {
        model.addAttribute("page", "staff.home");
        model.addAttribute("title_main", "Trang chủ quản lý hóa đơn hàng ngày ");
        return "index";
    }

    @RequestMapping("/staff/product")
    public String products(Model model, @ModelAttribute("auth") Authority auth) {
        try {
            model.addAttribute("items", productSevice.findAll());
            model.addAttribute("page", "staff.product");
            model.addAttribute("title_main", "Thông tin chi tiết các sản phẩm");
            model.addAttribute("_", auth.getAccount());  // cái này thêm để nó báo lỗi thì chuyển sang login
            return "index";
        } catch (Exception e) {
            return "redirect:/login.fastshop.com";
        }
    }


    @RequestMapping("/staff/detail/{id}")
    public String details(Model model, @ModelAttribute("auth") Authority auth, @PathVariable("id") Integer id) {
        try {
            Product product = productSevice.findById(id);
            model.addAttribute("product", product);
            model.addAttribute("page", "staff.detail");
            model.addAttribute("details", categoryDetailService.findByCategory(product.getCategory()));
            model.addAttribute("_", auth.getAccount());  // cái này thêm để nó báo lỗi thì chuyển sang login
            model.addAttribute("title_main", "Form thông tin chi tiết mô tả sản phẩm");
            return "index";
        } catch (Exception e) {
            return "redirect:/login.fastshop.com";
        }
    }

    @RequestMapping("/staff/describe/{id}")
    public String change(Model model, @RequestParam("describe") String describe, @PathVariable("id") Integer id) {
        try {
            Product product = productSevice.findById(id);
            product.setDescribe(describe);
            productSevice.save(product);
            model.addAttribute("page", "staff.detail");
            return "redirect:/staff/detail/" + id;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login.fastshop.com";
        }
    }


/**
 * Làm thế nào để lưu thông tin vô trong product detail
 * category detail và category là cố định không thay đổi j hết
 * có thể dùng rest để thực thi, mỗi một detail đc in ra từ trang detail.html được coi là 1 product detail nên khó lưu
 * @param model
 * @param describe
 * @param id
 * @return
 */
    @RequestMapping("/staff/redetail/{id}")
    public String redetail(Model model, @RequestParam("describe") String describe, @PathVariable("id") Integer id) {
        try {
            model.addAttribute("page", "staff.detail");
            return "redirect:/staff/detail/" + id;
        } catch (Exception e) {
            e.printStackTrace();
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
