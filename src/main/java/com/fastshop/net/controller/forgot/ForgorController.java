package com.fastshop.net.controller.forgot;

import org.springframework.stereotype.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.AccountDTO;

import com.fastshop.net.service._CookieService;
import com.fastshop.net.service._MailService;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service.CategoryService;
import com.fastshop.net.service.HistoryService;
import com.fastshop.net.service.ProductService;
import com.fastshop.net.service._SessionService;
@Controller
public class ForgorController {
    @Autowired
    _CookieService cookie;
    @Autowired
    _SessionService session;
    @Autowired
    _MailService mailService;
    @Autowired
    _SessionService sessionService;
    @Autowired
    AuthorityService authService;
    @Autowired
    AccountService accountService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    HistoryService historyService;
    @Autowired
    ProductService cartSevice;
    
    @RequestMapping("/forgot/access")
	 public String sendMail(Model model, @RequestParam("email") String email) {
		try {
			Account account = accountService.findByEmail(email);
			if (account != null) {
				cookie.add("email", email, 1*24*60);
				mailService.send(
                    email, 
                    "Xác nhận tài khoản của bạn",
                    "<a href='http://localhost:8080/ChangeForgot'><button class='btn btn-primary'>Xác nhận</button></a>"
                );

				return "success/success";
			} else {
                model.addAttribute("error2", "Không tìm thấy email của bạn !");
				return "redirect:/forgot";
			}
		} catch (Exception e) {
			e.printStackTrace();
            return "redirect:/forgot";
		}
	}

    @RequestMapping("/ChangeForgot")
	public String ChangeForgot(Model model) {
        model.addAttribute("accounts", new AccountDTO());
        model.addAttribute("account", cookie.getValue("email"));
		return "change-forgot";
	}

    @RequestMapping("/ChangeForgotPassword")
    public String uploadPassword(Model model, @ModelAttribute("accounts") AccountDTO accountDTO) {
        if (accountDTO.getNewPassword().isEmpty()) {
            model.addAttribute("error1", "Mật khẩu mới cần phải được nhập ...");
        }
        if (accountDTO.getConfirmPassword().isEmpty()) {
            model.addAttribute("error2", "Mật khẩu Xác nhận cần được nhập ...");
        }

        if (!accountDTO.getNewPassword().equals(accountDTO.getConfirmPassword())) {
            model.addAttribute("error2", "Mật khẩu không khớp ...");
        }

        try {
            if (accountDTO.getNewPassword().equals(accountDTO.getConfirmPassword())) {
                String email = cookie.getValue("email");
                Account account = accountService.findByEmail(email);
                account.setPassword(accountDTO.getConfirmPassword());
                accountService.save(account);
                cookie.remove("email");
            }
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "change-forgot";
        }
    }
}
