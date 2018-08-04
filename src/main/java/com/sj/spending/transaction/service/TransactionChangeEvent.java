package com.sj.spending.transaction.service;

import com.sj.spending.logic.data.Transaction;
import org.springframework.context.ApplicationEvent;

public class TransactionChangeEvent extends ApplicationEvent {

    final private Transaction changedTransaction;

    public TransactionChangeEvent(Object source, Transaction changedTransaction) {
        super(source);

        this.changedTransaction = changedTransaction;
    }

    public Transaction getChangedTransaction() {
        return changedTransaction;
    }

}
