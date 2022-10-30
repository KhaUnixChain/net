package com.fastshop.net.controller.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.ProductDetail;
import com.fastshop.net.model.ProductDetailBackup;
import com.fastshop.net.service.ProductDetailService;

@RestController
public class RestProductDetailController {
    @Autowired
    ProductDetailService productDetailService;

    @GetMapping("/rest/product/detail/all")
    public List<ProductDetail> getAll() {
        return productDetailService.findAll();
    }

    // get detail của product id
    @GetMapping("/rest/product/detail/{id}")
    public List<ProductDetailBackup> findByProductId(@PathVariable("id") Integer id) {
        return productDetailService.getByProductId(id);
    }
    
    // chỉ lấy thuộc tính và catgorydetail id
    @GetMapping("/rest/product/detail/map/{id}")
    public Map<String, String> getAllOfCategoryDetailId(@PathVariable("id") Integer id) {
        return productDetailService.getAllOfCategoryDetailAndInfo(id);
    };

    // kiểm tra record đó có tồn tại hay ko
    @GetMapping("/rest/product/detail/exist/{id}")
    public Boolean existAnyProductDetailByProductId(@PathVariable("id") Integer id) {
        return productDetailService.existAnyProductDetailByProductId(id);
    };

    // thêm product detail
    @PostMapping("/rest/product/detail")
    public void create(@RequestBody ProductDetail productDetail) {
        productDetailService.save(productDetail);
    }

    // cập nhật thông tin của product id
    @PutMapping("/rest/product/detail/{id}")
    public void update(@PathVariable("id") Long id) {
        ProductDetail productDetail = productDetailService.findById(id);
        if (productDetail != null) {
            productDetailService.save(productDetail);
        }
    }

    @DeleteMapping("/rest/product/detail/{id}")
    public void deteteByObject(@PathVariable("id") Long id) {
        productDetailService.deleteById(id);
    }
}
