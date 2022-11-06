package com.fastshop.net.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.model.Category;
import com.fastshop.net.model.History;
import com.fastshop.net.model.Product;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service.CategoryService;
import com.fastshop.net.service.HistoryService;
import com.fastshop.net.service.OrderDetailService;
import com.fastshop.net.service.OrderService;
import com.fastshop.net.service._CookieService;
import com.fastshop.net.service._SessionService;

@Controller
public class AdminController {
    @Autowired
    _CookieService cookie;
    @Autowired
    _SessionService session;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    AccountService accountService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    HistoryService historyService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;

    @RequestMapping("/admin/home")
    public String home(Model model, @ModelAttribute("auth") Authority authority) {
        try {
            String title_main = "Admin - Thống kê doanh số bán hàng";
            model.addAttribute("page", "admin.home");
            model.addAttribute("title_main", title_main);
            model.addAttribute("_", authority.getAccount());
            model.addAttribute("totalRevenue", Math.round(orderDetailService.getTotalRevenue()));
            model.addAttribute("totalOrder", orderDetailService.getTotalOrder());
            model.addAttribute("top3Product", orderDetailService.getTop3BestSelling());
            
            // thêm history
            if (authority != null) {
                History history = new History();
                history.setTitle(title_main);
                history.setLink("http://localhost:8080/admin/home");
                history.setSchedual(new Date());
                history.setAccount(authority.getAccount());
                historyService.save(history);   
            }
            return "index";
        } catch (Exception e) {
            return "redirect:/login.fastshop.com";
        }
    }


    @RequestMapping("/admin/product")
    public String product(Model model, @ModelAttribute("auth") Authority authority) {
        try {
            String title_main = "Admin - Thống kê danh sách sản phẩm";
            model.addAttribute("page", "admin.product");
            model.addAttribute("product", new Product());
            model.addAttribute("title_main", title_main);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("_", authority.getAccount());

            // thêm history
            if (authority != null) {
                History history = new History();
                history.setTitle(title_main);
                history.setLink("http://localhost:8080/admin/product");
                history.setSchedual(new Date());
                history.setAccount(authority.getAccount());
                historyService.save(history);   
            }
            return "index";
        } catch (Exception e) {
            return "redirect:/login.fastshop.com";
        }
    }


    @RequestMapping("/admin/category/{status}")
    public String category(Model model, @PathVariable("status") String status, @ModelAttribute("auth") Authority authority) {
        try {
            model.addAttribute("page", "admin.category");
            model.addAttribute("category", new Category());
            model.addAttribute("_", authority.getAccount());
            String title_main = "";

            if (status.equals("stock")) {
                title_main = "Admin - Danh sách phân loại còn bán";
                model.addAttribute("focus", "stock");
                model.addAttribute("title", "LOẠI HÀNG CÒN HOẠT ĐỘNG");
                model.addAttribute("title_main", title_main);
                model.addAttribute("categories", categoryService.findByStatus(true));
            }
            else if (status.equals("out")) {
                title_main = "Admin - Danh sách phân loại hết hàng";
                model.addAttribute("focus", "out");
                model.addAttribute("title", "LOẠI HÀNG ĐÃ TẠM DỪNG");
                model.addAttribute("title_main", title_main);
                model.addAttribute("categories", categoryService.findByStatus(false));
            } 
            else {
                title_main = "Admin - Danh sách tất cả phân loại";
                model.addAttribute("focus", "all");
                model.addAttribute("title", "TẤT CẢ LOẠI HÀNG");
                model.addAttribute("title_main", title_main);
                model.addAttribute("categories", categoryService.findAll());
            }

            // thêm history
            if (authority != null) {
                History history = new History();
                history.setTitle(title_main);
                history.setLink("http://localhost:8080/admin/category/" + status);
                history.setSchedual(new Date());
                history.setAccount(authority.getAccount());
                historyService.save(history);            
            }
            return "index";
        } catch (Exception e) {
            return "redirect:/login.fastshop.com";
        }
    }


    @RequestMapping("/admin/employee")
    public String customer(Model model, @ModelAttribute("auth") Authority authority) {
        try {
            String title_main = "Admin - Thống kê danh sách nhân viên";
            model.addAttribute("staff", new Account());
            model.addAttribute("title_main", title_main);
            model.addAttribute("employees", authorityService.getListStaff());
            model.addAttribute("page", "admin.employee");
            model.addAttribute("_", authority.getAccount());

            // thêm history
            if (authority != null) {
                History history = new History();
                history.setTitle(title_main);
                history.setLink("http://localhost:8080/admin/employee");
                history.setSchedual(new Date());
                history.setAccount(authority.getAccount());
                historyService.save(history);   
            }
            return "index";
        } catch (Exception e) {
            return "redirect:/login.fastshop.com";
        }
    }


    @RequestMapping("/admin/history")
    public String history(Model model, @ModelAttribute("auth") Authority authority) {
        try {
            String title_main = "Admin - Lịch sử hoạt động";
            model.addAttribute("histories", historyService.findAll());
            model.addAttribute("page", "admin.history");
            model.addAttribute("title_main", title_main);
            model.addAttribute("_", authority.getAccount());
            return "index";
        } catch (Exception e) {
            return "redirect:/login.fastshop.com";
        }
    }


    @RequestMapping("/admin/help/docs")
    public String docs(Model model, @ModelAttribute("auth") Authority authority) {
        String title_main = "Tài liệu hỗ trợ phần mềm";
        model.addAttribute("page", "admin.docs");
        model.addAttribute("title_main", title_main);

        // thêm history
        if (authority != null) {
            History history = new History();
            history.setTitle(title_main);
            history.setLink("http://localhost:8080/admin/help/docs");
            history.setSchedual(new Date());
            history.setAccount(authority.getAccount());
            historyService.save(history);
        }
        
        return "index";
    }


    @RequestMapping("/admin/help/license")
    public String license(Model model, @ModelAttribute("auth") Authority authority) {
        String title_main  = "Tài liệu thông tin pháp lý";
        model.addAttribute("page", "admin.license");
        model.addAttribute("title_main", title_main);

        // thêm history
        if (authority != null) {
            History history = new History();
            history.setTitle(title_main);
            history.setLink("http://localhost:8080/admin/help/license");
            history.setSchedual(new Date());
            history.setAccount(authority.getAccount());
            historyService.save(history);
        }
        
        return "index";
    }


    @RequestMapping("/admin/help/contact")
    public String contact(Model model, @ModelAttribute("auth") Authority authority) {
        String title_main = "Thông tin liên hệ chúng tôi";
        model.addAttribute("page", "admin.contact");
        model.addAttribute("title_main", title_main);

        // thêm history
        if (authority != null) {
            History history = new History();
            history.setTitle(title_main);
            history.setLink("http://localhost:8080/admin/help/contact");
            history.setSchedual(new Date());
            history.setAccount(authority.getAccount());
            historyService.save(history);   
        }
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
