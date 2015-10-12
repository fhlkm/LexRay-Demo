package com.lextech.model;

import java.util.List;

/**
 * Created by hanlu Feng on 10/12/2015.
 */
public class LexData {
    private List<Transactions> transactions;
    private String generated;

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
    }

    public String getGenerated() {
        return generated;
    }

    public void setGenerated(String generated) {
        this.generated = generated;
    }
}
