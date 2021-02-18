package com.payment.service;

import com.payment.dto.PaymentDto;
import com.payment.parser.PaymentParser;
import com.payment.parser.factory.PaymentParserFactory;
import com.payment.parser.model.PaymentRecord;
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
        Optional<PaymentParser> parser = paymentParserFactory.getParser(fileName);

        if (!parser.isPresent()) {
            String errorMessage = String.format("Not found parser for file %s", fileName);
            log.error(errorMessage);
            return;
        }

        PaymentParser paymentParser = parser.get();
        executor.submit(() -> outPaymentsToConsole(fileName, paymentParser));
    }

    private void outPaymentsToConsole(String fileName, PaymentParser paymentParser) {
        List<PaymentRecord> paymentRecords = paymentParser.parsePaymentRecord(fileName);
        IntStream.range(0, paymentRecords.size())
                .forEach(lineNumber -> System.out.println(wrap(paymentRecords.get(lineNumber), fileName, lineNumber + 1)));
    }

    private PaymentDto wrap(PaymentRecord paymentRecord, String file, int line) {
        return PaymentDto.builder()
                .filename(file)
                .line(line)
                .amount(paymentRecord.getAmount())
                .result(paymentRecord.getParserResult())
                .comment(paymentRecord.getComment())
                .id(paymentRecord.getOrderId())
                .build();
    }
}
