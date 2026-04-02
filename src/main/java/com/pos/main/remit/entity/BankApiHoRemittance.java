package com.pos.main.remit.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.pos.main.approval.entity.ApprovalGroup;
import com.pos.main.approval.entity.ApprovalStatus;
import com.pos.main.pos.entity.Plazas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bank_api_ho_remittance")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankApiHoRemittance {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_api_ho_remittance_seq")
	@SequenceGenerator(name = "bank_api_ho_remittance_seq", sequenceName = "BANK_API_HO_REMITTANCE_SEQ", allocationSize = 1)
	private Long id;
	
	private String requestId;
	
	private Long csdRemitDtlId;

    @Column(name = "code", nullable = false)
    private String code;

    //  Plaza
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plaza_id", nullable = false)
    private Plazas plaza;

    //  RemitBank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remit_bank_id", nullable = false)
    private RemitBank remitBank;

    @Column(name = "remit_bank_branch", nullable = false)
    private String remitBankBranch;

    @Column(name = "bank_receipt_no", nullable = false)
    private String bankReceiptNo;

    @Column(name = "remit_amount", nullable = false)
    private BigDecimal remitAmount;

    @Column(name = "remit_mode", nullable = false)
    private String remitMode;

    @Column(name = "remit_ref_no")
    private String remitRefNo;

    @Column(name = "remit_date", nullable = false)
    private LocalDateTime remitDate;

    //  RemitBankAccount
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remit_bank_account_id", nullable = false)
    private RemitBankAccount remitBankAccount;

    @Column(name = "matching_keyword")
    private String matchingKeyword;

    @Column(name = "remarks")
    private String remarks;

    //  ApprovalGroup
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_serve_group_id", nullable = false)
    private ApprovalGroup currentServeGroup;

    // 🔗 ApprovalStatus
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curr_status_id", nullable = false)
    private ApprovalStatus currStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prev_status_id", nullable = false)
    private ApprovalStatus prevStatus;

    @Column(name = "is_cleared", nullable = false)
    private Boolean isCleared;

    @Column(name = "cleared_date")
    private LocalDateTime clearedDate;

    @Column(name = "cleared_by")
    private String clearedBy;

    @Column(name = "is_reversed")
    private Boolean isReversed;

    @Column(name = "reversed_date")
    private LocalDateTime reversedDate;

    @Column(name = "reversed_by")
    private String reversedBy;

    @Column(name = "is_canceled")
    private Boolean isCanceled;

    @Column(name = "canceled_by")
    private String canceledBy;

    @Column(name = "canceled_date")
    private LocalDateTime canceledDate;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "is_new_system")
    private Boolean isNewSystem = false;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "modified_by")
    private String modifiedBy;

}
