package com.fastshop.net.controller.address;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fastshop.net.model.ATM;
import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.service.ATMService;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AddressService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service._CookieService;

@Controller
public class WalletController {
    @Autowired
    _CookieService cookie;
    @Autowired
    AddressService addressService;
    @Autowired
    ATMService atmService;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    AccountService accountService;
    @Autowired
    HttpServletRequest request;

    @RequestMapping("/user/wallet/remove")
    public String save(Model model, @ModelAttribute("auth") Authority auth) {
        ATM atm = atmService.findByAccount(auth.getAccount());
        atm.setValid(false);
        atmService.save(atm);
        return "redirect:/user/wallet";
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
