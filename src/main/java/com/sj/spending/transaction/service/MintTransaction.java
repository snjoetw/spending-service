package com.sj.spending.transaction.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class MintTransaction {
    private long id;
    private boolean isPending;
    private boolean isSpending;
    private boolean isDebit;
    private String amount;
    private String merchant;
    private String category;
    private String account;
    private String date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("isPending")
    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    @JsonProperty("isSpending")
    public boolean isSpending() {
        return isSpending;
    }

    public void setSpending(boolean spending) {
        isSpending = spending;
    }

    @JsonProperty("isDebit")
    public boolean isDebit() {
        return isDebit;
    }

    public void setDebit(boolean debit) {
        isDebit = debit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("isPending", isPending)
                .add("isSpending", isSpending)
                .add("isDebit", isDebit)
                .add("amount", amount)
                .add("merchant", merchant)
                .add("category", category)
                .add("account", account)
                .toString();
    }

}
