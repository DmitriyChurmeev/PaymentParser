package com.payment.parser.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

/**
 * Payment record
 *
 * @author Churmeev Dmitriy
 * @since 16.02.2021
 */
public class PaymentRecord {

    @CsvBindByName(required = true, column = "Идентификатор ордера")
    private Long orderId;
    @CsvBindByName(required = true, column = "сумма")
    private BigDecimal amount;
    @CsvBindByName(required = true, column = "валюта")
    private CurrencyType currency;
    @CsvBindByName(required = true, column = "комментарий")
    private String comment;

    @JsonCreator
    public PaymentRecord(@JsonProperty("orderId") Long orderId,
                         @JsonProperty("amount") BigDecimal amount,
                         @JsonProperty("currency") CurrencyType currency,
                         @JsonProperty("comment") String comment) {
        this.orderId = requireNonNull(orderId);
        this.amount = requireNonNull(amount);
        this.currency = requireNonNull(currency);
        this.comment = requireNonNull(comment);
    }

    public PaymentRecord() {

    }

    public Long getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "orderId=" + orderId +
                ", amount=" + amount +
                ", currency=" + currency +
                ", comment='" + comment + '\'' +
                '}';
    }
}
