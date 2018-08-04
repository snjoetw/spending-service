package com.sj.spending.transaction.data.entity;

import com.google.common.base.MoreObjects;
import com.sj.spending.logic.data.Account;
import com.sj.spending.logic.data.ImportSource;
import com.sj.spending.logic.data.ImportedTransaction;
import com.sj.spending.logic.data.ImportedTransactionState;
import com.sj.spending.logic.data.Money;
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
@Table(name = ImportedTransactionEntity.ENTITY_NAME)
public class ImportedTransactionEntity extends TimestampedEntity implements ImportedTransaction {

    static final String ENTITY_NAME = "ImportedTransaction";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "imported_transaction_id", nullable = false, updatable = false)
    private Long importedTransactionId;

    @Column(name = "source_transaction_id", nullable = false, updatable = false)
    private Long sourceTransactionId;

    @Column(name = "source_category_name")
    private String sourceCategoryName;

    @Enumerated(EnumType.STRING)
    @Column(name = "import_source")
    private ImportSource importSource;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private ImportedTransactionState state;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "account")
    private Account account;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "amount"))
    })
    private Money amount;

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
        return importSource;
    }

    @Override
    public void setImportSource(ImportSource importSource) {
        this.importSource = importSource;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("importedTransactionId", getImportedTransactionId())
                .add("sourceTransactionId", getSourceTransactionId())
                .add("sourceCategoryName", getSourceCategoryName())
                .add("importSource", getImportSource())
                .add("state", getState())
                .add("transactionType", getTransactionType())
                .add("transactionDate", getTransactionDate())
                .add("categoryId", getCategoryId())
                .add("description", getDescription())
                .add("amount", getAmount())
                .add("account", getAccount())
                .toString();
    }

    public static <T extends ImportedTransaction> ImportedTransactionEntity from(T other) {
        ImportedTransactionEntity entity = new ImportedTransactionEntity();
        ImportedTransaction.copy(other, entity);
        return entity;
    }

}
