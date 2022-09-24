package com.fastshop.net.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Order;
import com.fastshop.net.repository.OrderDAO;
import com.fastshop.net.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderDAO orderDAO;

    @Override
    public String findAddressByUsername(Account account) {
        try {
            return orderDAO.findAddressByUsername(account).get();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    @Override
    public void save(Order order) {
        orderDAO.save(order);
    }

    @Override
    public void deleteById(Long id) {
        orderDAO.deleteById(id);
    }

    @Override
    public Order findById(Long id) {
        return orderDAO.findById(id).get();
    }
    
}
