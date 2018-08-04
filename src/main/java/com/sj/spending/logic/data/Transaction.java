package com.sj.spending.logic.data;

import java.util.Date;

public interface Transaction {

    Long getTransactionId();

    void setTransactionId(Long transactionId);

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

    String getNotes();

    void setNotes(String notes);

    boolean isDeleted();

    void setDeleted(boolean deleted);

    Account getAccount();

    void setAccount(Account account);

    Long getImportedTransactionId();

    void setImportedTransactionId(Long importedTransactionId);

    static <FROM extends Transaction, TO extends Transaction> void copy(FROM from, TO to) {
        to.setTransactionId(from.getTransactionId());
        to.setTransactionType(from.getTransactionType());
        to.setTransactionDate(from.getTransactionDate());
        to.setCategoryId(from.getCategoryId());
        to.setDescription(from.getDescription());
        to.setAmount(from.getAmount());
        to.setNotes(from.getNotes());
        to.setDeleted(from.isDeleted());
        to.setAccount(from.getAccount());
        to.setImportedTransactionId(from.getImportedTransactionId());
    }

}
