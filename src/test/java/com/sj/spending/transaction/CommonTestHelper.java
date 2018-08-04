package com.sj.spending.transaction;

import com.sj.spending.logic.data.TransactionType;
import com.sj.spending.logic.data.Money;
import com.sj.spending.logic.data.Transaction;
import com.sj.spending.transaction.data.entity.CategoryEntity;
import com.sj.spending.transaction.data.entity.TransactionEntity;
import com.sj.spending.transaction.data.dto.TransactionDto;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CommonTestHelper {

    public static CategoryEntity createCategory(String name, Long parentId, boolean deleted) {
        CategoryEntity category = new CategoryEntity();
        category.setDeleted(deleted);
        category.setName(name);
        category.setParentCategoryId(parentId);
        return category;
    }

    public static void assertCategory(CategoryEntity category, long id, String name, Long parentId, boolean deleted) {
        assertThat(category.getCategoryId(), is(id));
        assertThat(category.getName(), is(name));
        assertThat(category.getParentCategoryId(), is(parentId));
        assertThat(category.isDeleted(), is(deleted));
    }

    public static TransactionEntity createTransactionEntity(TransactionType transactionType, Date transactionDate, long categoryId, String description, Money amount) {
        return createTransaction(
                new TransactionEntity(),
                null,
                transactionType,
                transactionDate,
                categoryId,
                description,
                amount,
                false
        );
    }

    public static TransactionEntity createTransactionEntity(Long transactionId, TransactionType transactionType, Date transactionDate, long categoryId, String description, Money amount, boolean isDeleted) {
        return createTransaction(
                new TransactionEntity(),
                transactionId,
                transactionType,
                transactionDate,
                categoryId,
                description,
                amount,
                isDeleted
        );
    }

    public static TransactionDto createTransactionDto(Long transactionId, TransactionType transactionType, Date transactionDate, long categoryId, String description, Money amount, boolean isDeleted) {
        return createTransaction(
                new TransactionDto(),
                transactionId,
                transactionType,
                transactionDate,
                categoryId,
                description,
                amount,
                isDeleted
        );
    }

    private static <T extends Transaction> T createTransaction(T transaction, Long transactionId, TransactionType transactionType, Date transactionDate, long categoryId, String description, Money amount, boolean isDeleted) {
        transaction.setTransactionId(transactionId);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionDate(transactionDate);
        transaction.setCategoryId(categoryId);
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setDeleted(isDeleted);
        return transaction;
    }

    public static <T extends Transaction> void assertTransaction(T transaction, Long id, TransactionType transactionType, Date transactionDate, long categoryId, String description, Money amount, boolean deleted) {
        assertThat(transaction.getTransactionId(), is(id));
        assertThat(transaction.getTransactionType(), is(transactionType));
        assertThat(transaction.getTransactionDate(), is(transactionDate));
        assertThat(transaction.getCategoryId(), is(categoryId));
        assertThat(transaction.getDescription(), is(description));
        assertThat(transaction.getAmount(), is(amount));
        assertThat(transaction.isDeleted(), is(deleted));
    }

}
