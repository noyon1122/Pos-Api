package com.pos.main.utils.entity;

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
@Table(name = "all_code_defination")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllCodeDef {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "all_code_defination_seq")
	@SequenceGenerator(name = "all_code_defination_seq", sequenceName = "ALL_CODE_DEFINATION_SEQ", allocationSize = 1)
	private Integer id;
	@Column(nullable = false)
	private String code;
	private String tableName;
	private Integer cdLength;
	private String dscrptn;
	private Integer nxtVal;
	private String pojoClass;
	private String prefix;
	private Integer plazaId;
	
}
