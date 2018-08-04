package com.sj.spending.transaction.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sj.spending.logic.data.Account;
import com.sj.spending.logic.data.ImportSource;
import com.sj.spending.logic.data.ImportedTransaction;
import com.sj.spending.logic.data.ImportedTransactionState;
import com.sj.spending.logic.data.Money;
import com.sj.spending.logic.data.TransactionType;

import java.util.Date;

public class ImportedTransactionDto implements ImportedTransaction {

    private Long importedTransactionId;

    private Long sourceTransactionId;

    private String sourceCategoryName;

    private ImportSource source;

    private ImportedTransactionState state;

    private TransactionType transactionType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "PST")
    private Date transactionDate;

    private Long categoryId;

    private String description;

    private Money amount;

    private Account account;

    @Override
    public Long getImportedTransactionId() {
        return importedTransactionId;
    }

    @Override
    public void setImportedTransactionId(Long importedTransactionId) {
        this.importedTransactionId = importedTransactionId;
    }

    @Override
    public Long getSourceTransactionId() {
        return sourceTransactionId;
    }

    @Override
    public void setSourceTransactionId(Long sourceTransactionId) {
        this.sourceTransactionId = sourceTransactionId;
    }

    @Override
    public String getSourceCategoryName() {
        return sourceCategoryName;
    }

    @Override
    public void setSourceCategoryName(String sourceCategoryName) {
        this.sourceCategoryName = sourceCategoryName;
    }

    @Override
    public ImportSource getImportSource() {
        return source;
    }

    @Override
    public void setImportSource(ImportSource source) {
        this.source = source;
    }

    @Override
    public ImportedTransactionState getState() {
        return state;
    }

    @Override
    public void setState(ImportedTransactionState state) {
        this.state = state;
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
    public Account getAccount() {
        return account;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    public static <T extends ImportedTransaction> ImportedTransactionDto from(T other) {
        ImportedTransactionDto dto = new ImportedTransactionDto();
        ImportedTransaction.copy(other, dto);
        return dto;
    }

}
