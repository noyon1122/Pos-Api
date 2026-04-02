package com.pos.main.clearing.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.pos.main.pos.entity.Plazas;
import com.pos.main.remit.entity.RemitBank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cl_pos_machines")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosMachine {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cl_pos_machines_seq")
	@SequenceGenerator(name = "cl_pos_machines_seq", sequenceName = "CL_POS_MACHINES_SEQ", allocationSize = 1)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "remit_bank_id", nullable = false)
    private RemitBank remitBank;
	
	@ManyToOne
    @JoinColumn(name = "plaza_id", nullable = false)
    private Plazas plaza;

    @Column(name = "store_id", nullable = false)
    private String storeId;

    @Column(name = "is_active")
    private Boolean isActive = true;

}
