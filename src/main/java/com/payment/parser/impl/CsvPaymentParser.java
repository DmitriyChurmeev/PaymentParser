package com.payment.parser.impl;

import com.opencsv.CSVReader;
import com.payment.dto.ParserResult;
import com.payment.parser.PaymentParser;
import com.payment.parser.model.CurrencyType;
import com.payment.parser.model.PaymentRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Parser payment records from CSV file
 *
 * @author Churmeev Dmitriy
 * @since 16.02.2021
 */
public class CsvPaymentParser implements PaymentParser {

    private static final Log log = LogFactory.getLog(CsvPaymentParser.class);

    @Override
    public List<PaymentRecord> parsePaymentRecord(String file) {
        try {
            List<PaymentRecord> records = new ArrayList<>();
            try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
                String[] values = csvReader.readNext();
                while ((values = csvReader.readNext()) != null) {
                    records.add(createRecord(Arrays.asList(values)));
                }
            }
            return records;
        } catch (Exception e) {
            String errorMessage = "Error during parse CSV file %s";
            log.error(String.format(errorMessage, file), e);
            return Collections.emptyList();
        }
    }

    private PaymentRecord createRecord(List<Object> values) {
        try {
            Long orderId = Long.valueOf(values.get(0).toString());
            Double amount = Double.valueOf(values.get(1).toString());
            CurrencyType currency = CurrencyType.valueOf(values.get(2).toString());
            String comment = values.get(3).toString();

            return new PaymentRecord(orderId, amount, currency, comment, ParserResult.OK);
        } catch (Exception e) {
            log.error("Error during parse payment record" + e.getMessage());
            return new PaymentRecord(ParserResult.FAIL);
        }
    }
}
