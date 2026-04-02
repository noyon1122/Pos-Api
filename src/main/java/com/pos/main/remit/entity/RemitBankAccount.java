package com.pos.main.remit.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "remit_bank_accounts")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemitBankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "remit_bank_accounts_seq")
	@SequenceGenerator(name = "remit_bank_accounts_seq", sequenceName = "REMIT_BANK_ACCOUNTS_SEQ", allocationSize = 1)
	private Long id;
	
    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Column(name = "account_no", nullable = false)
    private String accountNo;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

}
