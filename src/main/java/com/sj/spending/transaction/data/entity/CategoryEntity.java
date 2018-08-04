package com.sj.spending.transaction.data.entity;

import com.google.common.base.MoreObjects;
import com.sj.spending.logic.data.Category;
import com.sj.spending.logic.data.TransactionType;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = CategoryEntity.ENTITY_NAME)
@Cacheable(true)
public class CategoryEntity extends TimestampedEntity implements Category {

    static final String ENTITY_NAME = "Category";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id", nullable = false, updatable = false)
    private Long categoryId;

    @Column(name = "parent_category_id")
    private Long parentCategoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "name")
    private String name;

    @Column(name = "is_deleted")
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

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("categoryId", getCategoryId())
                .add("parentCategoryId", getParentCategoryId())
                .add("name", getName())
                .add("deleted", isDeleted())
                .toString();
    }

}
