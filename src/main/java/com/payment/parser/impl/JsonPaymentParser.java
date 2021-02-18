package com.payment.parser.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.parser.model.PaymentRecord;
import com.payment.parser.PaymentParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Parser payment records from JSON file
 *
 * @author Churmeev Dmitriy
 * @since 16.02.2021
 */
public class JsonPaymentParser implements PaymentParser {

    private static final Log log = LogFactory.getLog(JsonPaymentParser.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<PaymentRecord> parse(String file) {
        try {
            return Arrays.asList(mapper.readValue(Paths.get(file).toFile(), PaymentRecord[].class));
        } catch (IOException e) {
            String errorMessage = "Error during parse JSON file %s";
            log.error(String.format(errorMessage, file), e);
            return Collections.emptyList();
        }
    }
}
