package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.ProductDetail;

public interface ProductDetailService {
    List<ProductDetail> findByProductIdAndCategoryId(Integer productId, String categoryDetailId);
}
