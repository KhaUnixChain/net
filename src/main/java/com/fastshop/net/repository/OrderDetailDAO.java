package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.Order;
import com.fastshop.net.model.OrderDetail;

@Repository
public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long>{
    List<OrderDetail> findByOrder(Order order);
}
