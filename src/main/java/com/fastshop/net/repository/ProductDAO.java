package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.Category;
import com.fastshop.net.model.Product;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer>{
    List<Product> findByCategory(Category category);
    boolean existsById(Integer id);

    @Query("SELECT o FROM Product o WHERE o.name LIKE %:kw%")
    List<Product> findByKeywordName(String kw);
}
