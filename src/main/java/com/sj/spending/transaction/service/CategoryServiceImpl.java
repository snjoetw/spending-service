package com.sj.spending.transaction.service;

import com.google.common.collect.Sets;
import com.sj.spending.transaction.data.dto.CategoryDto;
import com.sj.spending.transaction.data.entity.CategoryEntity;
import com.sj.spending.transaction.data.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final HashSet<Long> IGNORED_CATEGORY_IDS = Sets.newHashSet(1L, 7L);

    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CategoryDto> findCategories() {
        Map<Long, CategoryDto> dtosById = repository.findAll().stream()
                .map(t -> CategoryDto.from(t))
                .collect(Collectors.toMap(x -> x.getCategoryId(), x -> x));

        return dtosById.values().stream()
                .filter(t -> !IGNORED_CATEGORY_IDS.contains(t.getCategoryId()))
                .map(t -> {
                    t.setCompleteName(getCompleteCategoryName(dtosById, t));
                    return t;
                })
                .sorted(Comparator.comparing(CategoryDto::getCompleteName))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findCategory(Long categoryId) {
        Optional<CategoryEntity> categoryEntity = repository.findById(categoryId);
        return categoryEntity.map(t -> CategoryDto.from(t)).orElse(null);
    }

    private String getCompleteCategoryName(Map<Long, CategoryDto> dtosById, CategoryDto current) {
        if (current.getParentCategoryId() == null || IGNORED_CATEGORY_IDS.contains(current.getParentCategoryId())) {
            return current.getName();
        }

        CategoryDto parent = dtosById.get(current.getParentCategoryId());
        String partial = getCompleteCategoryName(dtosById, parent);
        return partial == null ? current.getName() : partial + " / " + current.getName();
    }

}
