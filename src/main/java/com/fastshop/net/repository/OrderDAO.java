package com.fastshop.net.repository;

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
}
