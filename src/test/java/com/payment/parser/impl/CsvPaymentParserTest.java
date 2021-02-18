package com.payment.parser.impl;

import com.payment.dto.ParserResult;
import com.payment.parser.model.CurrencyType;
import com.payment.parser.model.PaymentRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/***
 * @author Churmeev Dmitriy
 * @since 16.02.2021
 */
class CsvPaymentParserTest {

    private CsvPaymentParser csvPaymentParser;

    @BeforeEach
    public void setUp() {
        csvPaymentParser = new CsvPaymentParser();
    }

    @Test
    void testParse_validCsvFile_returnListPayments() {
        String resourceName = "com/payment/valid_csv_payments.csv";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        String absolutePath = file.getAbsolutePath();
        List<PaymentRecord> paymentRecords = csvPaymentParser.parsePaymentRecord(absolutePath);
        PaymentRecord paymentRecord = paymentRecords.iterator().next();
        assertNotNull(paymentRecord);
        assertEquals(2, paymentRecords.size());
        assertEquals(1L, paymentRecord.getOrderId());
        assertEquals(Double.valueOf(100), paymentRecord.getAmount());
        assertEquals(CurrencyType.USD, paymentRecord.getCurrency());
        assertEquals(ParserResult.OK, paymentRecord.getParserResult());
    }

    @Test
    void testParse_invalidCsvFile_returnListPayments() {
        String resourceName = "com/payment/invalid_csv_payments.csv";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        String absolutePath = file.getAbsolutePath();
        List<PaymentRecord> paymentRecords = csvPaymentParser.parsePaymentRecord(absolutePath);
        PaymentRecord paymentRecord = paymentRecords.iterator().next();
        assertNotNull(paymentRecord);
        assertEquals(ParserResult.FAIL, paymentRecord.getParserResult());
    }
}