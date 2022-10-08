package com.fastshop.net.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Category;
import com.fastshop.net.model.Product;
import com.fastshop.net.repository.CategoryDAO;
import com.fastshop.net.repository.ProductDAO;
import com.fastshop.net.service.ProductSevice;

@Service
public class ProductServiceImpl implements ProductSevice{
    @Autowired
    ProductDAO productDAO;
    @Autowired
    CategoryDAO categoryDAO;

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public Product findById(Integer id) {
        return productDAO.findById(id).get();
    }

    @Override
    public List<Product> findByCategoryId(String cid) {
        Category category = categoryDAO.findById(cid).get();
        return productDAO.findByCategory(category);
    }

    @Override
    public void save(Product product) {
        productDAO.save(product);
    }

    @Override
    public void deleteById(Integer id) {
        productDAO.deleteById(id);
    }

    @Override
    public List<Product> findByKeywordName(String kw) {
        return productDAO.findByKeywordName(kw);
    }
}
