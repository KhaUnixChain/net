package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fastshop.net.model.Order;
import com.fastshop.net.model.OrderDetail;
import com.fastshop.net.model.Product;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long>{
    List<OrderDetail> findByOrder(Order order);
    @Query("select SUM(od.quantity*od.product.price) from OrderDetail od where MONTH(GETDATE()) = MONTH(od.order.createDate)")
    Double getTotalRevenue();
    @Query("select SUM(od.quantity) from OrderDetail od where MONTH(GETDATE()) = MONTH(od.order.createDate)")
    Integer getTotalOrder();
    @Query("SELECT COUNT(od.product) FROM OrderDetail od WHERE od.product = ?1")
    Integer countByProduct(Product product);
    @Query("select od.product.name from OrderDetail od where MONTH(GETDATE()) = MONTH(od.order.createDate) group by od.product.name order by SUM(od.quantity) DESC")
    List<String> getTop3BestSelling();
}
