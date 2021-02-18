package com.payment.dto;

import lombok.Builder;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

/**
 * Dto of Payment record
 *
 * @author Churmeev Dmitriy
 * @since 18.02.2021
 */
@Builder
public class PaymentDto {

    private final Long id;
    private final BigDecimal amount;
    private final String comment;
    private final String filename;
    private final Integer line;
    private final PaymentResult result;

    public PaymentDto(Long id,
                      BigDecimal amount,
                      String comment,
                      String filename,
                      Integer line,
                      PaymentResult result) {
        this.id = requireNonNull(id);
        this.amount = requireNonNull(amount);
        this.comment = requireNonNull(comment);
        this.filename = filename;
        this.line = line;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public String getFilename() {
        return filename;
    }

    public Integer getLine() {
        return line;
    }

    public PaymentResult getResult() {
        return result;
    }


    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", amount:" + amount +
                ", comment:'" + comment + '\'' +
                ", filename:'" + filename + '\'' +
                ", line:" + line +
                ", result:'" + result + '\'' +
                '}';
    }
}
