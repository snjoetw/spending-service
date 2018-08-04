package com.sj.spending.logic.data;

public interface Category {

    Long getCategoryId();

    void setCategoryId(Long categoryId);

    Long getParentCategoryId();

    void setParentCategoryId(Long parentCategoryId);

    TransactionType getTransactionType();

    void setTransactionType(TransactionType transactionType);

    String getName();

    void setName(String name);

    boolean isDeleted();

    void setDeleted(boolean deleted);

    static <FROM extends Category, TO extends Category> void copy(FROM from, TO to) {
        to.setCategoryId(from.getCategoryId());
        to.setParentCategoryId(from.getParentCategoryId());
        to.setTransactionType(from.getTransactionType());
        to.setName(from.getName());
        to.setDeleted(from.isDeleted());
    }
}
