package com.fastshop.net.service;

import com.fastshop.net.model.ATM;

public interface ATMService {
    ATM findById(Integer id);
    ATM findByUsername(String username);
    ATM findByNumber(String number);
}
