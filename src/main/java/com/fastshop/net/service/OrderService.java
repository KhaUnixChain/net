package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Order;

public interface OrderService {
    void save(Order order);
    void deleteById(Long id);
    Order findById(Long id);
    String findAddressByUsername(Account account);
    List<Order> findAll();
}
