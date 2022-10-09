package com.fastshop.net.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
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
import com.fastshop.net.service.AddressService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service.CategoryService;
import com.fastshop.net.service.OrderService;
import com.fastshop.net.service.ProductDetailService;

@Controller
public class CustomerController {
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
    OrderService orderService;
    @Autowired
    AddressService addressService;
    @Autowired
    ProductDetailService productDetailService;
    @Autowired
    private ApplicationContext applicationContext;
    
    @RequestMapping("/user/home")
    public String home(Model model, @ModelAttribute("auth") Authority auth, @RequestParam("cid") Optional<String> cid) {
        List<Product> list;
        if (cid.isPresent()) {
            list = productSevice.findByCategoryId(cid.get());
        }
        else {
            list = productSevice.findAll();
        }
        int number_hint_keyword = 5;
        model.addAttribute("products", list);
        model.addAttribute("page", "user.home");
        model.addAttribute("title_main", "Fastshop.com - Nơi những mặt hàng được vận chuyển nhanh chóng mặt");
        model.addAttribute("hints", categoryService.getOneProductEachCategories(number_hint_keyword));
        return "index";
    }

    @RequestMapping("/user/search")
    public String search(Model model, @RequestParam("keyword") String kw) {
        List<Product> list = null;
        list = productSevice.findByKeywordName(kw);
        int number_hint_keyword = 5;
        model.addAttribute("products", list);
        model.addAttribute("page", "user.home");
        model.addAttribute("title_main", "Fastshop.com - Nơi những mặt hàng được vận chuyển nhanh chóng mặt");
        model.addAttribute("hints", categoryService.getOneProductEachCategories(number_hint_keyword));
        return "index";
    }


    @RequestMapping("/user/filter")
    public String filter(Model model, 
                         @RequestParam("rate") Integer rate, 
                         @RequestParam("cateId") String cateId, 
                         @RequestParam("priceFrom") Integer priceFrom, 
                         @RequestParam("priceTo") Integer priceTo)
    {
        List<Product> list = null;
        list = productSevice.findByFilter(rate, cateId, priceFrom, priceTo);
        int number_hint_keyword = 5;
        model.addAttribute("products", list);
        model.addAttribute("page", "user.home");
        model.addAttribute("title_main", "Fastshop.com - Nơi những mặt hàng được vận chuyển nhanh chóng mặt");
        model.addAttribute("hints", categoryService.getOneProductEachCategories(number_hint_keyword));
        System.out.println(list.size());
        return "index";
    }

    
    @RequestMapping("/user/detail/{id}")
    public String detailUser(Model model, @ModelAttribute("auth") Authority auth, @PathVariable("id") Integer id) {
        Product product = productSevice.findById(id);
        model.addAttribute("page", "user.detail");
        model.addAttribute("title_main", "Fastshop - Chi tiết sản phẩm");
        model.addAttribute("product", product);
        try {
            model.addAttribute("address", addressService.findByAccountWithChooseIsTrue(auth.getAccount().getUsername()));
            return "index";
        } catch (Exception e) {
            model.addAttribute("address", null);
            return "index";
        }
    }

    
    @RequestMapping("/user/checkout")
    public String checkout(Model model, @ModelAttribute("auth") Authority auth) {
        try {
            model.addAttribute("page", "user.checkout");
            model.addAttribute("now", new Date());
            model.addAttribute("title_main", "Thanh toán sản phẩm của bạn");
            model.addAttribute("address", orderService.findAddressByUsername(auth.getAccount()));
            model.addAttribute("products", productSevice.findAll());
            return "index";
        } catch (Exception e) {
            return "redirect:/login";
        }
        
    }


    @RequestMapping("/user/history")
    public String history(Model model, @ModelAttribute("auth") Authority auth) {
        try {
            model.addAttribute("page", "user.history");
            model.addAttribute("now", new Date());
            model.addAttribute("address", orderService.findAddressByUsername(auth.getAccount()));  // thêm để khi cố ý đổi link thì tự động vô login
            return "index";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }

    
    @RequestMapping("/user/myorder")
    public String myorder(Model model, @ModelAttribute("auth") Authority auth) {
        try {
            model.addAttribute("page", "user.myorder");
            model.addAttribute("title_main", "Fastshop - Quản lý hóa đơn");
            model.addAttribute("orders", orderService.findAll());
            model.addAttribute("address", orderService.findAddressByUsername(auth.getAccount()));
            return "index";
        } catch (Exception e) {
            return "redirect:/login";
        }
        
    }


    @RequestMapping("/user/address")
    public String address(Model model, @ModelAttribute("auth") Authority auth) {
        try {
            model.addAttribute("page", "user.address");
            model.addAttribute("addresses", addressService.findAllAddressByAccount(auth.getAccount().getUsername()));
            model.addAttribute("address", addressService.findByAccountWithChooseIsTrue(auth.getAccount().getUsername()));
            model.addAttribute("title_main", "Fastshop - Địa chỉ mặc định của bạn");
            model.addAttribute("_", orderService.findAddressByUsername(auth.getAccount()));  // thêm để khi cố ý đổi link thì tự động vô login
            return "index";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }


    @RequestMapping("/user/wallet")
    public String wallet(Model model, @ModelAttribute("auth") Authority auth) {
        try {
            model.addAttribute("page", "user.wallet");
            model.addAttribute("title_main", "Fastshop - Thông tin hồ sơ tài khoản thanh toán");
            model.addAttribute("address", orderService.findAddressByUsername(auth.getAccount()));  // thêm để khi cố ý đổi link thì tự động vô login
            return "index";
        } catch (Exception e) {
             return "redirect:/login";
        }
    }


    @RequestMapping("/user/discount")
    public String discount(Model model, @ModelAttribute("auth") Authority auth) {
        try {
            model.addAttribute("page", "user.discount");
            model.addAttribute("title_main", "Fastshop - Ưu đãi của bạn ở đây");
            model.addAttribute("address", orderService.findAddressByUsername(auth.getAccount()));  // thêm để khi cố ý đổi link thì tự động vô login
            return "index";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }


    @RequestMapping("/user/sales")
    public String sales(Model model, @ModelAttribute("auth") Authority auth) {
        model.addAttribute("page", "user.sales");
        model.addAttribute("title_main", "Fastshop - Free mua hàng, nhận quà liền tay");
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


    @ModelAttribute("page")
    public String getPage() {
        String page = "";
        String username = cookie.getValue("username");
        if (username != null) {
            Account account = accountService.findByUsernameOrEmail(username, username);
            Authority authority = authorityService.findByAccount(account);
            page = (authority.getRole().getId().equals("ADMIN")) ? "admin.home" : "";
        }
        return page;
    }

    @ModelAttribute("files")
    public Resource[] files() throws IOException {
        Resource[] resources_hot = applicationContext.getResources("classpath*:/static/hot/*");
        return resources_hot;
    }

    @ModelAttribute("discount")
    public Resource[] discount() throws IOException {
        Resource[] resources_dis = applicationContext.getResources("classpath*:/static/dist/img/discount/*");
        return resources_dis;
    }
}
