package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.Product;
import com.fastshop.net.model.ProductDetail;

public interface ProductDetailService {
    List<ProductDetail> findByProduct(Product product);
}
