package com.payment.parser.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.payment.dto.ParserResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Payment record
 *
 * @author Churmeev Dmitriy
 * @since 16.02.2021
 */
@Getter
@Setter
@ToString
public class PaymentRecord {

    private Long orderId;
    private Double amount;
    private CurrencyType currency;
    private String comment;
    private ParserResult parserResult;

    @JsonCreator
    public PaymentRecord(@JsonProperty("orderId") Long orderId,
                         @JsonProperty("amount") Double amount,
                         @JsonProperty("currency") CurrencyType currency,
                         @JsonProperty("comment") String comment,
                         @JsonProperty("parserResult") ParserResult parserResult) {
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.comment = comment;
        this.parserResult = parserResult;
    }

    public PaymentRecord(ParserResult parserResult) {
        this.parserResult = parserResult;
    }
}
