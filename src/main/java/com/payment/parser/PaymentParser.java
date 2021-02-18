package com.payment.parser;

import com.payment.parser.model.PaymentRecord;

import java.util.List;

/**
 * Interface for parse payment records
 *
 * @author Churmeev Dmitriy
 * @since 16.02.2021
 */
public interface PaymentParser {

    /**
     * Parse file and return List of Payment Record from file
     * @param file file name with Payment Record
     * @return List Payment Record {@link PaymentRecord}
     */
    List<PaymentRecord> parsePaymentRecord(String file);
}
