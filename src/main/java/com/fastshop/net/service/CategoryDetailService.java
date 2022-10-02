package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.Category;
import com.fastshop.net.model.CategoryDetail;

public interface CategoryDetailService {
    
    List<CategoryDetail> findByCategory(Category category);
}
