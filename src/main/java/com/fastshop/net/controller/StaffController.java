package com.fastshop.net.controller;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.model.Category;
import com.fastshop.net.model.Order;
import com.fastshop.net.model.Product;
import com.fastshop.net.model.ProductDetail;
import com.fastshop.net.model.Status;
import com.fastshop.net.service.ProductService;
import com.fastshop.net.service.StatusService;
import com.fastshop.net.service._CookieService;
import com.fastshop.net.service._GetListFile;
import com.fastshop.net.utils.FormatDate;
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
    @Autowired
    StatusService statusService;
    
    @RequestMapping("/staff/home")
    public String home(Model model, @ModelAttribute("auth") Authority auth) {
        try {
            Date toNow = java.sql.Date.valueOf(LocalDate.now());
            model.addAttribute("page", "staff.home");
            model.addAttribute("ordertoday", orderService.getAllOfOrderToday(new Date()));
            model.addAttribute("orderNotToday", orderService.findNotByCreateDate(toNow));
            model.addAttribute("title_main", "Trang chủ quản lý hóa đơn hàng ngày ");
            model.addAttribute("_", auth.getAccount());  // cái này thêm để nó báo lỗi thì chuyển sang login
            return "index";
        } catch (Exception e) {
            return "redirect:/login.fastshop.com";
        }
        
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


    @RequestMapping("/staff/products")
    public String product(Model model, @ModelAttribute("auth") Authority authority) {
        try {
            String title_main = "User - Quản lý sản phẩm";
            model.addAttribute("page", "staff.products");
            model.addAttribute("product", new Product());
            model.addAttribute("title_main", title_main);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("_", authority.getAccount());
            return "index";
        } catch (Exception e) {
            return "redirect:/login.fastshop.com";
        }
    }


    @RequestMapping("/staff/category/{status}")
    public String category(Model model, @PathVariable("status") String status, @ModelAttribute("auth") Authority authority) {
        try {
            model.addAttribute("page", "staff.category");
            model.addAttribute("category", new Category());
            model.addAttribute("_", authority.getAccount());
            String title_main = "";

            if (status.equals("stock")) {
                title_main = "Staff - Danh sách phân loại còn bán";
                model.addAttribute("focus", "stock");
                model.addAttribute("title", "LOẠI HÀNG CÒN HOẠT ĐỘNG");
                model.addAttribute("title_main", title_main);
                model.addAttribute("categories", categoryService.findByStatus(true));
            }
            else if (status.equals("out")) {
                title_main = "Staff - Danh sách phân loại hết hàng";
                model.addAttribute("focus", "out");
                model.addAttribute("title", "LOẠI HÀNG ĐÃ TẠM DỪNG");
                model.addAttribute("title_main", title_main);
                model.addAttribute("categories", categoryService.findByStatus(false));
            } 
            else {
                title_main = "Staff - Danh sách tất cả phân loại";
                model.addAttribute("focus", "all");
                model.addAttribute("title", "TẤT CẢ LOẠI HÀNG");
                model.addAttribute("title_main", title_main);
                model.addAttribute("categories", categoryService.findAll());
            }
            return "index";
        } catch (Exception e) {
            return "redirect:/login.fastshop.com";
        }
    }


    @RequestMapping("/staff/report")
    public String report(Model model, @ModelAttribute("auth") Authority auth) {
        try {
            model.addAttribute("page", "staff.report");
            model.addAttribute("now", FormatDate.parse());
            model.addAttribute("title_main", "Báo cáo hoạch toán hằng ngày");
            model.addAttribute("products", productSevice.findAll());
            model.addAttribute("_", auth.getAccount());  // cái này thêm để nó báo lỗi thì chuyển sang login
            return "index";
        } catch (Exception e) {
            return "redirect:/login.fastshop.com";
        }
    }


    // function nhan nut xac nhan
    @RequestMapping("/staff/confirm/{id}")
    public String confirm(@RequestParam("status") Integer kind, @PathVariable("id") Long orderId) {
        try {
            Order order = orderService.findById(orderId);
            Status status = statusService.findById(kind);
            order.setStatus(status);
            order.setDateConfirm(new Date());
            orderService.save(order);
            return "redirect:/staff/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login.fastshop.com";
        }
    }


    @RequestMapping("/staff/detail/{id}")
    public String details(Model model, @ModelAttribute("auth") Authority auth, @PathVariable("id") Integer id) {
        try {
            Product product = productSevice.findById(id);
            model.addAttribute("product", product);
            model.addAttribute("productDetail", new ProductDetail());
            model.addAttribute("page", "staff.detail");
            model.addAttribute("productdetails", productDetailService.getByProductId(id));
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

    @RequestMapping("/staff/discount")
    public String dicsount(Model model, @ModelAttribute("auth") Authority auth) {
        try {
            model.addAttribute("page", "staff.discount");
            model.addAttribute("_", auth.getAccount());  // cái này thêm để nó báo lỗi thì chuyển sang login
            model.addAttribute("title_main", "Fastshop - Giảm giá sản phẩm");
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login.fastshop.com";
        }
    }

    
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
