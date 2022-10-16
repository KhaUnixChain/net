package com.fastshop.net.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDAO extends JpaRepository<Order, Long>{
    @Query("SELECT o.address FROM Order o WHERE o.account = ?1 GROUP BY o.address")
    Optional<String> findAddressByUsername(Account account);

    @Query("SELECT o FROM Order o WHERE o.createDate = ?1")
    List<Order> findByCreateDate(Date createDate);

    @Query("SELECT o FROM Order o WHERE o.createDate < ?1")
    List<Order> findNotByCreateDate(Date toNow);

    @Query("SELECT o FROM Order o WHERE o.account.email = ?1 OR o.account.phone = ?2")
    List<Order> findAllByEmailOrPhone(String email, String phone);

    @Query("SELECT o FROM Order o WHERE o.createDate BETWEEN ?1 AND ?2")
    List<Order> findByCreateDateBetween(Date from, Date to);

    @Query("SELECT o FROM Order o WHERE o.status.id = ?1")
    List<Order> findByStatus(Integer id);
}
