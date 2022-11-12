package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.Discount;

@Repository
public interface DiscountDAO extends JpaRepository<Discount, String>{
    List<Discount> findByDolarNotNull();
    List<Discount> findByFreeNotNull();

    @Query("SELECT o FROM Discount o WHERE o.free = ?1")
    List<Discount> findByFreeEqualNumber(Double free);
}
