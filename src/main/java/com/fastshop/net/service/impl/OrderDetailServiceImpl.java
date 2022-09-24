package com.fastshop.net.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.OrderDetail;
import com.fastshop.net.repository.OrderDetailDAO;
import com.fastshop.net.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{
    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Override
    public void save(OrderDetail orderDetail) {
        orderDetailDAO.save(orderDetail);
    }

    @Override
    public void deleteById(Long id) {
        orderDetailDAO.deleteById(id);
    }

    @Override
    public OrderDetail findById(Long id) {
        return orderDetailDAO.findById(id).get();
    }

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailDAO.findAll();
    }

    
}