package com.payment.service;

import com.payment.dto.PaymentDto;
import com.payment.dto.PaymentResult;
import com.payment.parser.model.PaymentRecord;
import com.payment.parser.PaymentParser;
import com.payment.parser.factory.PaymentParserFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Service for parsing payments and out information to console
 *
 * @author Churmeev Dmitriy
 * @since 16.02.2021
 */

@Service
public class PaymentService {

    private static final Log log = LogFactory.getLog(PaymentService.class);
    private static final int THREAD_POOL_SIZE = 100;
    private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    private final PaymentParserFactory paymentParserFactory;

    @Autowired
    public PaymentService(PaymentParserFactory paymentParserFactory) {
        this.paymentParserFactory = paymentParserFactory;
    }

    /**
     * Parse files to list Payment Dto {@link com.payment.parser.model.PaymentRecord} and write to console
     *
     * @param fileNames List of file name
     */
    public void parseFile(String... fileNames) {
        Arrays.stream(fileNames).forEach(this::parseFile);
    }

    /**
     * Parse file to list Payment Dto {@link com.payment.parser.model.PaymentRecord} and write to console
     *
     * @param fileName file name
     */
    public void parseFile(String fileName) {
        try {
            Optional<PaymentParser> parser = paymentParserFactory.getParser(fileName);

            if (parser.isEmpty()) {
                String errorMessage = String.format("Not found parser for file %s", fileName);
                log.error(errorMessage);
                return;
            }

            PaymentParser paymentParser = parser.get();
            executor.submit(() -> outPaymentsToConsole(fileName, paymentParser) );
        } catch (Exception e) {
            String errorMessage = String.format("Error during parse file %s", fileName);
            log.error(errorMessage, e);
        }
    }

    private void outPaymentsToConsole(String fileName, PaymentParser paymentParser) {
        List<PaymentRecord> paymentRecords = paymentParser.parse(fileName);
        IntStream.range(0, paymentRecords.size())
                .forEach(index -> System.out.println(wrap(paymentRecords.get(index), fileName, index + 1)));
    }

    private PaymentDto wrap(PaymentRecord paymentRecord, String file, int line) {
        return PaymentDto.builder()
                .filename(file)
                .line(line)
                .amount(paymentRecord.getAmount())
                .result(PaymentResult.OK)
                .comment(paymentRecord.getComment())
                .id(paymentRecord.getOrderId())
                .build();
    }

}
