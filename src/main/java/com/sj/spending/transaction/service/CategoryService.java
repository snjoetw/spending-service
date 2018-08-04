package com.sj.spending.transaction.service;

import com.sj.spending.transaction.data.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> findCategories();

    CategoryDto findCategory(Long categoryId);

}
