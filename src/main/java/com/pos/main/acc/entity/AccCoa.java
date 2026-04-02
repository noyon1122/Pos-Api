package com.pos.main.acc.entity;
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
@Table(name = "acc_coa")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccCoa {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_coa_seq")
	@SequenceGenerator(name = "acc_coa_seq", sequenceName = "ACC_COA_SEQ", allocationSize = 1)
	private Long id;
    @Column(name = "keyword", length = 255)
    private String keyword;
    @Column(name = "is_active")
    private Boolean isActive;

  


}
