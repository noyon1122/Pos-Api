package com.pos.main.acc.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import com.pos.main.pos.entity.Plazas;
import com.pos.main.remit.entity.RemitBank;
import com.pos.main.remit.entity.RemitBankAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "acc_ledger")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccLedger {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_ledger_seq")
	@SequenceGenerator(name = "acc_ledger_seq", sequenceName = "ACC_LEDGER_SEQ", allocationSize = 1)
	private Long id;
	
    @Column(name = "post_date", nullable = false)
    private LocalDate postDate;

    @Column(name = "gl_date", nullable = false)
    private LocalDate glDate;

    @Column(length = 1000)
    private String narration;

    @Column(length = 1000)
    private String particulars;

    private String refNo;
    private String refSource;
    private String voucherNo;
    private String voucherType;
    private String itemGroup;

    @Column(precision = 18, scale = 2)
    private BigDecimal creditAmount;

    @Column(precision = 18, scale = 2)
    private BigDecimal debitAmount;

    private LocalDateTime modified;
    private String modifiedBy;


    // RELATIONS

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_coa_ledger_id", nullable = false)
    private AccCoa accCoaLedgerIdAccCoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_coa_ref_id")
    private AccCoa accCoaRefIdAccCoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_dep_coa_id")
    private AccCoa accDepCoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_period_id")
    private AccPeriod accPeriodIdAccPeriod;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plaza_id")
    private Plazas plazas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_plaza_id")
    private Plazas refPlaza;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remit_bank_id")
    private RemitBank remitBank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remit_bank_account_id")
    private RemitBankAccount remitBankAccount;

}
