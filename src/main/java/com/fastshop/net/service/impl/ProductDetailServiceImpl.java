package com.fastshop.net.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Category;
import com.fastshop.net.model.CategoryDetail;
import com.fastshop.net.model.Product;
import com.fastshop.net.model.ProductDetail;
import com.fastshop.net.model.ProductDetailBackup;
import com.fastshop.net.repository.CategoryDAO;
import com.fastshop.net.repository.CategoryDetailDAO;
import com.fastshop.net.repository.ProductDetailDAO;
import com.fastshop.net.service.CategoryDetailService;
import com.fastshop.net.service.ProductDetailService;
import com.fastshop.net.service.ProductService;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    ProductDetailDAO productDetailDAO;
    @Autowired
    CategoryDAO categoryDAO;
    @Autowired
    CategoryDetailDAO categoryDetailDAO;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryDetailService categoryDetailService;

    @Override
    public List<ProductDetail> findByProductId(Integer productId) {
        return productDetailDAO.findByProductId(productId);
    }

    @Override
    public List<ProductDetailBackup> getByProductId(Integer productId) {
        List<ProductDetailBackup> list = new ArrayList<>();
        List<ProductDetail> productDetails = productDetailDAO.findByProductId(productId);
        if (productDetails.size() > 0) {
            for (ProductDetail productDetail : productDetails) {
                ProductDetailBackup pdb = new ProductDetailBackup();
                pdb.setProductId(productId);
                pdb.setInfo(productDetail.getInfo());
                pdb.setProperty(categoryDetailDAO.findById(productDetail.getCategoryDetailId()).get().getProperty());
                list.add(pdb);
            }
        }
        else {
            Product product = productService.findById(productId);
            Category category = product.getCategory();
            List<CategoryDetail> l = categoryDetailService.findByCategory(category);
            for (CategoryDetail cd : l) {
                ProductDetailBackup pdb = new ProductDetailBackup();
                pdb.setProductId(productId);
                pdb.setInfo("");
                pdb.setProperty(cd.getProperty());
                list.add(pdb);
            }
        }
        return list;
    }

}
