package com.pos.main.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankRequestDto implements Serializable {

	@NotBlank(message = "Remit Bank name is required")
    private String remitBank;

    @NotBlank(message = "Remit Bank Account is required")
    private String remitBankAccount;

    private String remitBankBranch;

    @NotNull(message = "Remit Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal remitAmount;

    @NotBlank(message = "Remit Mode is required")
    private String remitMode;

    @NotBlank(message = "Remit Reference No is required")
    private String remitRefNo;

    private String bankReceiptNo;

    @NotBlank(message = "Remit Date is required")
    private String remitDate;

    @NotBlank(message = "Request ID is required")
    private String requestId;
    @NotBlank(message = "Request Timestamp is required")
    private String requestTimestamp;

    @NotBlank(message = "Transaction ID is required")
    private String transactionId;

    @NotBlank(message = "VAM Code is required")
    private String vamCode;

    private String remarks;

    
    
}
