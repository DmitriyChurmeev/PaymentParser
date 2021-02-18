package com.payment.parser.factory;

import com.payment.parser.PaymentParser;
import com.payment.parser.impl.CsvPaymentParser;
import com.payment.parser.impl.JsonPaymentParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Factory for payment parser {@link PaymentParser}
 *
 * @author Churmeev Dmitriy
 * @since 16.02.2021
 */
@Service
public class PaymentParserFactory {

    private static final Log log = LogFactory.getLog(PaymentParserFactory.class);

    public Optional<PaymentParser> getParser(String fileName) {

        Optional<FileType> fileTypeFromFile = getFileTypeFormatFile(fileName);

        if (!fileTypeFromFile.isPresent()) {
            log.error("Not found file type for file " + fileName);
            return Optional.empty();
        }

        FileType fileType = fileTypeFromFile.get();
        switch (fileType) {
            case JSON:
                return Optional.of(new JsonPaymentParser());
            case CSV:
                return Optional.of(new CsvPaymentParser());
            default:
                log.error("Not found parser for type " + fileType);
                return Optional.empty();
        }
    }

    private Optional<FileType> getFileTypeFormatFile(String fileName) {
        return getExtensionFormatType(fileName)
                .flatMap(FileType::getFileTypeByExtension);
    }

    private Optional<String> getExtensionFormatType(String fileName) {
        return Optional.ofNullable(fileName)
                .filter(file -> file.contains("."))
                .map(file -> file.substring(fileName.lastIndexOf(".") + 1).toLowerCase());
    }

}
