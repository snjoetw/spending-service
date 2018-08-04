package com.sj.spending.logic.data;

import java.util.Date;

public interface ImportedTransaction {

    Long getImportedTransactionId();

    void setImportedTransactionId(Long importedTransactionId);

    Long getSourceTransactionId();

    void setSourceTransactionId(Long sourceTransactionId);

    String getSourceCategoryName();

    void setSourceCategoryName(String sourceCategoryName);

    ImportSource getImportSource();

    void setImportSource(ImportSource source);

    ImportedTransactionState getState();

    void setState(ImportedTransactionState state);

    TransactionType getTransactionType();

    void setTransactionType(TransactionType transactionType);

    Date getTransactionDate();

    void setTransactionDate(Date transactionDate);

    Long getCategoryId();

    void setCategoryId(Long categoryId);

    String getDescription();

    void setDescription(String description);

    Money getAmount();

    void setAmount(Money amount);

    Account getAccount();

    void setAccount(Account account);

    static <FROM extends ImportedTransaction, TO extends ImportedTransaction> void copy(FROM from, TO to) {
        to.setImportedTransactionId(from.getImportedTransactionId());
        to.setSourceTransactionId(from.getSourceTransactionId());
        to.setSourceCategoryName(from.getSourceCategoryName());
        to.setImportSource(from.getImportSource());
        to.setState(from.getState());
        to.setTransactionType(from.getTransactionType());
        to.setTransactionDate(from.getTransactionDate());
        to.setCategoryId(from.getCategoryId());
        to.setDescription(from.getDescription());
        to.setAmount(from.getAmount());
        to.setAccount(from.getAccount());
    }

}
