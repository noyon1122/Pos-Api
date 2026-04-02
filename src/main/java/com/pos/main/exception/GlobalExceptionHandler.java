package com.pos.main.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pos.main.api.dto.AuthResponse;
import com.pos.main.api.dto.BankResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	
	@ExceptionHandler({CustomException.class, BadCredentialsException.class})
    public ResponseEntity<AuthResponse> handleAuthErrors(Exception ex) {
        log.warn("Auth Error: {}", ex.getMessage());
        
        AuthResponse response = AuthResponse.builder()
                .statusCode(401)
                .message("Invalid username or password!") // সিকিউরিটির জন্য জেনেরিক মেসেজ
                .build();
                
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
	@ExceptionHandler(BankApiException.class)
    public ResponseEntity<BankResponse> handleBankApiException(BankApiException ex) {

        log.warn("Business exception occurred: {}", ex.getMessage());

        HttpStatus status = HttpStatus.resolve(ex.getStatusCode());
        status = (status != null) ? status : HttpStatus.INTERNAL_SERVER_ERROR;


        BankResponse response = new BankResponse(
                ex.getStatusCode(),
                ex.getRequestId(),
                ex.getMessage()
        );

        return new ResponseEntity<>(response, status);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<BankResponse> handleGeneralException(Exception ex) {
        log.error("Unhandled exception occurred", ex);

        BankResponse response = new BankResponse(
                500,
                "SYSTEM",
                "Internal Server Error"
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}