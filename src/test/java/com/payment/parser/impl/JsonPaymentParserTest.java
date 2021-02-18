package com.payment.parser.impl;

import com.payment.dto.ParserResult;
import com.payment.parser.model.PaymentRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * @author Churmeev Dmitriy
 * @since 16.02.2021
 */
class JsonPaymentParserTest {

    private JsonPaymentParser jsonPaymentParser;

    @BeforeEach
    public void setUp() {
        jsonPaymentParser = new JsonPaymentParser();
    }

    @Test
    void testParse_validJson_returnListPaymentRecords() {
        String resourceName = "com/payment/valid_json_payments.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        String absolutePath = file.getAbsolutePath();
        List<PaymentRecord> paymentRecords = jsonPaymentParser.parsePaymentRecord(absolutePath);
        PaymentRecord paymentRecord = paymentRecords.iterator().next();
        assertNotNull(paymentRecord);
        assertEquals(3, paymentRecords.size());
        assertEquals(Long.valueOf(1), paymentRecord.getOrderId());
        assertEquals(1.23, paymentRecord.getAmount());
        assertEquals("оплата заказа", paymentRecord.getComment());
        assertEquals(ParserResult.OK, paymentRecord.getParserResult());
    }

    @Test
    void testParse_invalidJson_returnListFailPaymentRecord() {
        String resourceName = "com/payment/invalid_json_payments.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        String absolutePath = file.getAbsolutePath();
        List<PaymentRecord> paymentRecords = jsonPaymentParser.parsePaymentRecord(absolutePath);
        PaymentRecord paymentRecord = paymentRecords.iterator().next();
        assertNotNull(paymentRecord);
        assertEquals(ParserResult.FAIL, paymentRecord.getParserResult());
    }
}