package com.payment.parser.impl;

import com.payment.parser.model.CurrencyType;
import com.payment.parser.model.PaymentRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
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
        List<PaymentRecord> paymentRecords = jsonPaymentParser.parse(absolutePath);
        PaymentRecord paymentRecord = paymentRecords.iterator().next();
        assertNotNull(paymentRecord);
        assertEquals(3, paymentRecords.size());
        assertEquals(Long.valueOf(1), paymentRecord.getOrderId());
        assertEquals(BigDecimal.valueOf(1.23), paymentRecord.getAmount());
        assertEquals(CurrencyType.USD, paymentRecord.getCurrency());
        assertEquals("оплата заказа", paymentRecord.getComment());
    }
}