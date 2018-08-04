package com.sj.spending.transaction.data.entity;

import com.google.common.base.MoreObjects;
import com.sj.spending.logic.data.Account;
import com.sj.spending.logic.data.Money;
import com.sj.spending.logic.data.Transaction;
import com.sj.spending.logic.data.TransactionType;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = TransactionEntity.ENTITY_NAME)
public class TransactionEntity extends TimestampedEntity implements Transaction {

    static final String ENTITY_NAME = "Transaction";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "transaction_id", nullable = false, updatable = false)
    private Long transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "name")
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "amount"))
    })
    private Money amount;

    @Column(name = "notes")
    private String notes;

    @Column(name = "is_deleted")
    private boolean deleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "account")
    private Account account;


    @Column(name = "imported_transaction_id", nullable = true)
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("transactionId", getTransactionId())
                .add("transactionType", getTransactionType())
                .add("transactionDate", getTransactionDate())
                .add("categoryId", getCategoryId())
                .add("description", getDescription())
                .add("amount", getAmount())
                .add("notes", getNotes())
                .add("deleted", isDeleted())
                .add("account", getAccount())
                .add("importedTransactionId", getImportedTransactionId())
                .toString();
    }

    public static <T extends Transaction> TransactionEntity from(T other) {
        TransactionEntity entity = new TransactionEntity();
        Transaction.copy(other, entity);
        return entity;
    }

}
