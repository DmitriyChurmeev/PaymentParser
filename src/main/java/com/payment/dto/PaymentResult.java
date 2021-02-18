package com.payment.dto;

/**
 *
 * Result of Payment record
 *
 * @author Churmeev Dmitriy
 * @since 18.02.2021
 */
public enum PaymentResult {
    /**
     * Success payment
     */
    OK,
    /**
     * Error during payment
     */
    ERROR,
    /**
     * Fail payment
     */
    FAIL,
}
