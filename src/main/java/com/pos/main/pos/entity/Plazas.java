package com.pos.main.pos.entity;

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
@Table(name = "pos_plazas")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plazas {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pos_plazas_seq")
	@SequenceGenerator(name = "pos_plazas_seq", sequenceName = "POS_PLAZAS_SEQ", allocationSize = 1)
	private Integer id;
	
	private String vamCode;
	
}
