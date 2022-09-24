package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.OrderDetail;

public interface OrderDetailService {
    void save(OrderDetail orderDetail);
    void deleteById(Long id);
    OrderDetail findById(Long id);
    List<OrderDetail> findAll();
}
