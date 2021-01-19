package com.stuff.payment.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Entity
public class Payment implements Serializable {

    @Min(0)
    private double amount;
    private String currency;
    private String userId;
    private String payeeId;
    private String payeeName;
    @Length(min = 2)
    private String paymentMethodId;
    private String paymentMethodName;
    @Id
    private String transactionId;
    private String risk;

    public Payment() {
        this.setTransactionId();
        this.setPayeeName();
        this.setPaymentMethodName();
    }

    public Payment(double amount, String currency, String userId, String payeeId, String paymentMethodId) {
        this.amount = amount;
        this.currency = currency;
        this.userId = userId;
        this.payeeId = payeeId;
        this.paymentMethodId = paymentMethodId;
        this.transactionId = UUID.randomUUID().toString();

        this.setTransactionId();
        this.setPayeeName();
        this.setPaymentMethodName();
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setTransactionId() {
        this.setTransactionId(UUID.randomUUID().toString());
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getUserId() {
        return userId;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public void setPayeeName() {

        List<String> names = new ArrayList<>();
        names.add("John");
        names.add("Lisa");
        names.add("Alex");
        names.add("Abby");
        names.add("Barbra");
        names.add("Mike");
        int randomNameIndex = new Random().nextInt(names.size());
        String randomName = names.get(randomNameIndex);

        this.setPayeeName(randomName);
    }


    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public void setPaymentMethodName() {

        List<String> paymentMethods = new ArrayList<>();
        paymentMethods.add("PayPal");
        paymentMethods.add("Cash");
        paymentMethods.add("Credit Card");
        paymentMethods.add("Bank Transfer");
        paymentMethods.add("Direct Deposit");
        paymentMethods.add("Google Pay");
        int randomPaymentMethodIndex = new Random().nextInt(paymentMethods.size());
        String randomPaymentMethod = paymentMethods.get(randomPaymentMethodIndex);

        this.setPaymentMethodName(randomPaymentMethod);

    }
}
