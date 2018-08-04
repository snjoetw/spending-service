package com.sj.spending.transaction.data.repo;

import com.sj.spending.transaction.data.entity.CategoryEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {

    @Override
    @CacheEvict("categories")
    CategoryEntity save(CategoryEntity category);

    @Override
    @CacheEvict("categories")
    void delete(CategoryEntity category);

    @Override
    @Cacheable("categories")
    boolean existsById(Long categoryId);

    @Override
    @Cacheable("categories")
    @Query("SELECT c FROM CategoryEntity c WHERE c.deleted = 0")
    Collection<CategoryEntity> findAll();

    @Override
    @Cacheable("categories")
    Optional<CategoryEntity> findById(Long categoryId);

}
