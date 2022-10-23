package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.ProductDetail;
import com.fastshop.net.model.ProductDetailBackup;

public interface ProductDetailService {
    List<ProductDetail> findByProductId(Integer productId);
    List<ProductDetailBackup> getByProductId(Integer productId);
}
