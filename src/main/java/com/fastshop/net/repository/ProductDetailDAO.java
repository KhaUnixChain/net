package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.ProductDetail;

@Repository
public interface ProductDetailDAO extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findByProductId(Integer productId);
}
