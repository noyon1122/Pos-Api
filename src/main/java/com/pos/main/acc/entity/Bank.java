package com.pos.main.acc.entity;

import java.time.LocalDateTime;

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
@Table(name = "acc_banks")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bank {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_banks_seq")
	@SequenceGenerator(name = "acc_banks_seq", sequenceName = "ACC_BANKS_SEQ", allocationSize = 1)
	private Long id;
	
	    @Column(name = "bank_name", nullable = false)
	    private String bankName;

	    @Column(name = "branch_name", nullable = false)
	    private String branchName;

	    @Column(name = "account_no", nullable = false)
	    private String accountNo;
	    @Column(nullable = false)
	    private LocalDateTime created;

	    @Column(name = "created_by", nullable = false)
	    private String createdBy;

	    @Column
	    private LocalDateTime modified;

	    @Column(name = "modified_by")
	    private String modifiedBy;

}
