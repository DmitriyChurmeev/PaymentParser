package com.payment.dto;

import lombok.Builder;

/**
 * Dto of Payment record
 *
 * @author Churmeev Dmitriy
 * @since 18.02.2021
 */
@Builder
public class PaymentDto {

    private final Long id;
    private final Double amount;
    private final String comment;
    private final String filename;
    private final Integer line;
    private final ParserResult result;

    public PaymentDto(Long id,
                      Double amount,
                      String comment,
                      String filename,
                      Integer line,
                      ParserResult result) {
        this.id = id;
        this.amount = amount;
        this.comment = comment;
        this.filename = filename;
        this.line = line;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
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

    public ParserResult getResult() {
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
