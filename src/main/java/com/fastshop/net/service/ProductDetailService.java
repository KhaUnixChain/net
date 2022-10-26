package com.fastshop.net.service;

import java.util.List;
import java.util.Map;

import com.fastshop.net.model.ProductDetail;
import com.fastshop.net.model.ProductDetailBackup;

public interface ProductDetailService {
    void save(ProductDetail productDetail);
    void delete(ProductDetail productDetail);
    void deleteById(Long id);
    List<ProductDetail> findAll();
    List<ProductDetail> findByProductId(Integer productId);
    List<ProductDetailBackup> getByProductId(Integer productId);
    Map<String, String> getAllOfCategoryDetailAndInfo(Integer productId);
}
