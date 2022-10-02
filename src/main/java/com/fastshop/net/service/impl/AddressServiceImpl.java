package com.fastshop.net.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.repository.AddressDAO;
import com.fastshop.net.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressDAO addressDAO;

    @Override
    public List<String> findAllAddressByAccount(String username) {
        return addressDAO.findAllAddressByAccount(username);
    }

    @Override
    public String findByAccountWithChooseIsTrue(String username) {
        return addressDAO.findByAccountWithChooseIsTrue(username).get();
    }
    
}
