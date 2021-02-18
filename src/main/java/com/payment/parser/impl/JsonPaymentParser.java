package com.payment.parser.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.dto.ParserResult;
import com.payment.parser.PaymentParser;
import com.payment.parser.model.PaymentRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    public List<PaymentRecord> parsePaymentRecord(String file) {
        try {
            List<PaymentRecord> records = new ArrayList<>();
            Arrays.asList(mapper.readValue(Paths.get(file).toFile(), Map[].class))
                    .forEach(mapValuesFromJsonFile -> records.add(createRecord(mapValuesFromJsonFile)));

            return records;
        } catch (IOException e) {
            String errorMessage = "Error during parse JSON file %s";
            log.error(String.format(errorMessage, file), e);
            return Collections.emptyList();
        }
    }

    private PaymentRecord createRecord(Map<Object, String> values) {
        try {
            PaymentRecord paymentRecord = mapper.convertValue(values, PaymentRecord.class);
            paymentRecord.setParserResult(ParserResult.OK);
            return paymentRecord;
        } catch (Exception e) {
            log.error("Error during parse payment record" + e.getMessage());
            return new PaymentRecord(ParserResult.FAIL);
        }
    }
}
