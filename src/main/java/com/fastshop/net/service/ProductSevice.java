package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.Product;

public interface ProductSevice {
    void save(Product product);
    void deleteById(Integer id);
    Product findById(Integer id);
    List<Product> findAll();
    List<Product> findByCategoryId(String cid);
    List<Product> findByKeywordName(String kw);
}
