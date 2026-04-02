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
@Table(name = "approval_group")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_group_seq")
	@SequenceGenerator(name = "approval_group_seq", sequenceName = "APPROVAL_GROUP_SEQ", allocationSize = 1)
	private Long id;
    @Column(name = "keyword", nullable = false)
    private String keyword;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

}
