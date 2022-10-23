package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.ProductDetail;

@Repository
public interface ProductDetailDAO extends JpaRepository<ProductDetail, Long> {
    @Query("SELECT o FROM ProductDetail o WHERE o.productId = ?1 AND o.categoryDetailId = ?2")
    List<ProductDetail> findByProductIdAndCategoryId(Integer productId, String categoryDetailId);
}
