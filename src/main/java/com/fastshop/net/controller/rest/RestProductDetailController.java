package com.fastshop.net.controller.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.ProductDetail;
import com.fastshop.net.service.ProductDetailService;

@RestController
public class RestProductDetailController {
    @Autowired
    ProductDetailService productDetailService;

    @GetMapping("/rest/product/detail/all")
    public List<ProductDetail> getAll() {
        return productDetailService.findAll();
    }

    @GetMapping("/rest/product/detail/{id}")
    public List<ProductDetail> findByProductId(@PathVariable("id") Integer id) {
        return productDetailService.findByProductId(id);
    }
    
    @GetMapping("/rest/product/detail/map/{id}")
    public Map<String, String> getAllOfCategoryDetailId(@PathVariable("id") Integer id) {
        return productDetailService.getAllOfCategoryDetailAndInfo(id);
    };
}
