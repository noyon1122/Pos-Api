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
@Table(name = "remit_banks")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemitBank {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "remit_banks_seq")
	@SequenceGenerator(name = "remit_banks_seq", sequenceName = "REMIT_BANKS_SEQ", allocationSize = 1)
	private Long id;
	
	@Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "bank_short_name")
    private String bankShortName;

}
