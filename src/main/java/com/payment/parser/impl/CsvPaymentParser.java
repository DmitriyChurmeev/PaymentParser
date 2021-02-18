package com.payment.parser.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import com.payment.parser.model.PaymentRecord;
import com.payment.parser.PaymentParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
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
    public List<PaymentRecord> parse(String file) {
        try {
            return new CsvToBeanBuilder<PaymentRecord>(new FileReader(file, StandardCharsets.UTF_8))
                    .withType(PaymentRecord.class)
                    .build()
                    .parse();

        } catch (Exception e) {
            String errorMessage = "Error during parse CSV file %s";
            log.error(String.format(errorMessage, file), e);
            return Collections.emptyList();
        }

    }

}
