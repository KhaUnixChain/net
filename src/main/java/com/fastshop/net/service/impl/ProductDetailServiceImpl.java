package com.fastshop.net.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Product;
import com.fastshop.net.model.ProductDetail;
import com.fastshop.net.repository.ProductDetailDAO;
import com.fastshop.net.service.ProductDetailService;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    ProductDetailDAO productDetailDAO;

    @Override
    public List<ProductDetail> findByProduct(Product product) {
        return productDetailDAO.findByProductId(product.getId());
    }
}
