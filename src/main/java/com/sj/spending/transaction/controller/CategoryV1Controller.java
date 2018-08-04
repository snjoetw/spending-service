package com.sj.spending.transaction.controller;

import com.sj.spending.transaction.data.dto.CategoryDto;
import com.sj.spending.transaction.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryV1Controller {

    private final CategoryService categoryService;

    @Autowired
    public CategoryV1Controller(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<CategoryDto> findCategories() {
        return categoryService.findCategories();
    }

}
