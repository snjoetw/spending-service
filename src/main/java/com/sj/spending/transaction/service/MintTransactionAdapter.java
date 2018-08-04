package com.sj.spending.transaction.service;

import com.google.common.collect.ImmutableMap;
import com.sj.spending.logic.data.Account;
import com.sj.spending.logic.data.Currency;
import com.sj.spending.logic.data.ImportSource;
import com.sj.spending.logic.data.ImportedTransaction;
import com.sj.spending.logic.data.ImportedTransactionState;
import com.sj.spending.logic.data.Money;
import com.sj.spending.logic.data.TransactionType;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.trim;

public class MintTransactionAdapter implements ImportedTransaction {

    private final MintTransaction transaction;

    public MintTransactionAdapter(MintTransaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public Long getImportedTransactionId() {
        return null;
    }

    @Override
    public void setImportedTransactionId(Long importedTransactionId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long getSourceTransactionId() {
        return transaction.getId();
    }

    @Override
    public void setSourceTransactionId(Long sourceTransactionId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSourceCategoryName() {
        return transaction.getCategory();
    }

    @Override
    public void setSourceCategoryName(String sourceCategoryName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImportSource getImportSource() {
        return ImportSource.MINT;
    }

    @Override
    public void setImportSource(ImportSource source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImportedTransactionState getState() {
        return ImportedTransactionState.PENDING;
    }

    @Override
    public void setState(ImportedTransactionState state) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TransactionType getTransactionType() {
        return transaction.isDebit() ? TransactionType.DEBIT : TransactionType.CREDIT;
    }

    @Override
    public void setTransactionType(TransactionType transactionType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Date getTransactionDate() {
        return TO_DATE.apply(transaction);
    }

    @Override
    public void setTransactionDate(Date transactionDate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long getCategoryId() {
        return TO_CATEGORY_ID.apply(transaction);
    }

    @Override
    public void setCategoryId(Long categoryId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getDescription() {
        return transaction.getMerchant();
    }

    @Override
    public void setDescription(String description) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Money getAmount() {
        String amountStr = trim(substringAfter(transaction.getAmount(), "$"));

        if (amountStr == null) {
            return null;
        }

        double amount = NumberUtils.toDouble(amountStr.replace(",", ""));
        Currency currency = TO_CURRENCY.apply(transaction);
        return amount == 0 ? null : new Money(currency, amount);
    }

    @Override
    public void setAmount(Money amount) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Account getAccount() {
        return TO_ACCOUNT.apply(transaction);
    }

    @Override
    public void setAccount(Account account) {
        throw new UnsupportedOperationException();
    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy MMM d");
    private static final Function<MintTransaction, Date> TO_DATE = t -> {
        String dateStr = t.getDate();

        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        LocalDate date = LocalDate.parse(LocalDate.now().getYear() + " " + dateStr, DATE_TIME_FORMATTER);
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    };

    private static final Map<String, Long> CATEGORY_ID_BY_MINT_CATEGORY_NAME = ImmutableMap.<String, Long>builder()
            .put("Air Travel", 19L)
            .put("Alcohol & Bars", 40L)
            .put("Amusement", 25L)
            .put("Babysitter & Daycare", 27L)
            .put("Clothing", 32L)
            .put("Coffee Shops", 3L)
            .put("Dentist", 26L)
            .put("Electronics & Software", 48L)
            .put("Entertainment", 25L)
            .put("Eyecare", 26L)
            .put("Fast Food", 40L)
            .put("Food & Dining", 40L)
            .put("Furnishings", 47L)
            .put("Gas & Fuel", 4L)
            .put("Gift", 22L)
            .put("Groceries", 22L)
            .put("Gym", 22L)
            .put("Hair", 30L)
            .put("Hobbies", 22L)
            .put("Home Improvement", 47L)
            .put("Home Services", 22L)
            .put("Kids", 22L)
            .put("Kids Activities", 22L)
            .put("Mobile Phone", 38L)
            .put("Parking", 13L)
            .put("Public Transportation", 13L)
            .put("Restaurants", 40L)
            .put("Service & Parts", 22L)
            .put("Shipping", 22L)
            .put("Shopping", 22L)
            .put("Spa & Massage", 22L)
            .put("Television", 38L)
            .put("Toys", 25L)
            .build();

    private static final long UNCATEGORIZED_ID = 22L;
    private static final Function<MintTransaction, Long> TO_CATEGORY_ID = t -> {
        String merchant = t.getMerchant();

        switch (merchant) {
            case "Costco":
            case "Wal-Mart":
            case "Choices Crest":
                return 10L;
            default:
                Long categoryId = CATEGORY_ID_BY_MINT_CATEGORY_NAME.get(t.getCategory());
                return categoryId == null ? UNCATEGORIZED_ID : categoryId;
        }

    };

    private static final Function<MintTransaction, Account> TO_ACCOUNT = t -> {
        String account = trim(t.getAccount());

        if (StringUtils.isEmpty(account)) {
            return Account.UNKNOWN;
        }

        switch (account) {
            case "Bank of America Cash Rewards Visa Signature":
                return Account.BOA_VISA;
            case "Costco Mastercard":
                return Account.COSTCO_MASTERCARD;
            case "TD FIRST CLASS TRAVEL VISA INFINITE CARD":
                return Account.TD_VISA;
            case "Blue Cash Everyday":
                return Account.AMEX_USA;
            case "The American Express Gold Rewards Card":
                return Account.AMEX_CANADA;
            case "Tangerine Money-Back Credit Card":
                return Account.TANGERINE_MASTERCARD;
            case "Scotia Momentum VISA Infinite":
                return Account.SCOTIA_VISA;
            case "TD US VISA":
                return Account.TD_US_VISA;
            default:
                return Account.UNKNOWN;
        }
    };

    private static final Function<MintTransaction, Currency> TO_CURRENCY = t -> {
        Account account = TO_ACCOUNT.apply(t);
        return account.getCurrency();
    };

}
