package com.fastshop.net.service;

import com.fastshop.net.model.ATM;
import com.fastshop.net.model.Account;

public interface ATMService {
    ATM findById(Integer id);
    ATM findByAccount(Account account);
    ATM findByNumber(String number);
}
