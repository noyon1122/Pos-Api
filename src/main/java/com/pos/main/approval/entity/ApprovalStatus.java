package com.pos.main.approval.entity;

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
@Table(name = "approval_status")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_status_seq")
	@SequenceGenerator(name = "approval_status_seq", sequenceName = "APPROVAL_STATUS_SEQ", allocationSize = 1)
	private Long id;

    @Column(name = "keyword", nullable = false, unique = true)
    private String keyword;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
