package com.payment.parser.factory;

import java.util.Arrays;
import java.util.Optional;

/**
 * File types that parser supports
 *
 * @author Churmeev Dmitriy
 * @since 18.02.2021
 */
public enum FileType {

    JSON("json"),
    CSV("csv");

    private String extension;

    FileType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static Optional<FileType> getFileTypeByExtension(String extension) {
        return Arrays.stream(FileType.values())
                .filter(fileType -> fileType.getExtension().equals(extension))
                .findAny();
    }
}
