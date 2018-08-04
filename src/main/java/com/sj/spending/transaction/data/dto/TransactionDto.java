package com.sj.spending.transaction.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sj.spending.logic.data.Account;
import com.sj.spending.logic.data.Money;
import com.sj.spending.logic.data.Transaction;
import com.sj.spending.logic.data.TransactionType;

import java.util.Date;

public class TransactionDto implements Transaction {

    private Long transactionId;

    private TransactionType transactionType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "PST")
    private Date transactionDate;

    private Long categoryId;

    private String description;

    private Money amount;

    private String notes;

    private boolean deleted;

    private Account account;

    private Long importedTransactionId;

    @Override
    public Long getTransactionId() {
        return transactionId;
    }

    @Override
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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
    public Date getTransactionDate() {
        return transactionDate;
    }

    @Override
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public Long getCategoryId() {
        return categoryId;
    }

    @Override
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Money getAmount() {
        return amount;
    }

    @Override
    public void setAmount(Money amount) {
        this.amount = amount;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public void setNotes(String notes) {
        this.notes = notes;
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
    public Account getAccount() {
        return account;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public Long getImportedTransactionId() {
        return importedTransactionId;
    }

    @Override
    public void setImportedTransactionId(Long importedTransactionId) {
        this.importedTransactionId = importedTransactionId;
    }

    public static <T extends Transaction> TransactionDto from(T other) {
        TransactionDto dto = new TransactionDto();
        Transaction.copy(other, dto);
        return dto;
    }

}
