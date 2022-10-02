package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.Category;
import com.fastshop.net.model.CategoryDetail;

@Repository
public interface CategoryDetailDAO extends JpaRepository<CategoryDetail, String> {
    List<CategoryDetail> findByCategory(Category category);
}
