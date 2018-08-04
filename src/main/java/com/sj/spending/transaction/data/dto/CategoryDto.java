package com.sj.spending.transaction.data.dto;

import com.sj.spending.logic.data.Category;
import com.sj.spending.logic.data.TransactionType;

public class CategoryDto implements Category {

    private String name;

    private String completeName;

    private Long categoryId;

    private Long parentCategoryId;

    private TransactionType transactionType;

    private boolean deleted;

    @Override
    public Long getCategoryId() {
        return categoryId;
    }

    @Override
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    @Override
    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    @Override
    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public static <T extends Category> CategoryDto from(T other) {
        CategoryDto dto = new CategoryDto();
        Category.copy(other, dto);
        return dto;
    }

}
