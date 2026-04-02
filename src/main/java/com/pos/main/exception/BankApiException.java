package com.pos.main.exception;

public class BankApiException extends RuntimeException {
    private final int statusCode;
    private final String requestId;

    public BankApiException(String message, int statusCode, String requestId) {
        super(message);
        this.statusCode = statusCode;
        this.requestId = requestId;
    }

    public int getStatusCode() { return statusCode; }
    public String getRequestId() { return requestId; }
}