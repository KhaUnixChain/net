package com.fastshop.net.controller.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fastshop.net.model.Category;
import com.fastshop.net.service.CategoryService;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/category/add")
    public String add(Category category) {
        category.setStatus(true);
        categoryService.save(category);
        return "redirect:/admin/category/stock";
    }

    @RequestMapping("/category/update/avaiable/{id}")
    public String update(@PathVariable("id") String id, @RequestParam("avaiable") Boolean avaiable, @RequestParam("focus") String focus) {
        categoryService.updateProductAvaiableFromCategory(id, avaiable);
        return "redirect:/admin/category/" + focus; 
    }
}
