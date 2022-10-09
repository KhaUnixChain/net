package com.fastshop.net.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.Product;
import com.fastshop.net.model.ProductDetail;
import com.fastshop.net.service.CategoryDetailService;
import com.fastshop.net.service.CategoryService;
import com.fastshop.net.service.ProductDetailService;
import com.fastshop.net.service.ProductService;

@RestController
public class RestProductDetailController {
    @Autowired
    ProductDetailService productDetailService;
    @Autowired
    CategoryDetailService categoryDetailService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;


    @GetMapping("/rest/productdetails/{id}")
    public List<ProductDetail> getAll(@PathVariable("id") Integer id) {
        Product product = productService.findById(id);
        return productDetailService.findByProduct(product);
    }
}
