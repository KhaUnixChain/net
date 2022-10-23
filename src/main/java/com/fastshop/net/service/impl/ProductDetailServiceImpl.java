package com.fastshop.net.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.ProductDetail;
import com.fastshop.net.model.ProductDetailBackup;
import com.fastshop.net.repository.CategoryDAO;
import com.fastshop.net.repository.CategoryDetailDAO;
import com.fastshop.net.repository.ProductDetailDAO;
import com.fastshop.net.service.ProductDetailService;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    ProductDetailDAO productDetailDAO;
    @Autowired
    CategoryDAO categoryDAO;
    @Autowired
    CategoryDetailDAO categoryDetailDAO;

    @Override
    public List<ProductDetail> findByProductId(Integer productId) {
        return productDetailDAO.findByProductId(productId);
    }

    @Override
    public List<ProductDetailBackup> getByProductId(Integer productId) {
        List<ProductDetailBackup> list = new ArrayList<>();
        List<ProductDetail> productDetails = productDetailDAO.findByProductId(productId);
        for (ProductDetail productDetail : productDetails) {
            ProductDetailBackup pdb = new ProductDetailBackup();
            pdb.setProductId(productId);
            pdb.setInfo(productDetail.getInfo());
            pdb.setProperty(categoryDetailDAO.findById(productDetail.getCategoryDetailId()).get().getProperty());
            list.add(pdb);
        }
        return list;
    }

}
