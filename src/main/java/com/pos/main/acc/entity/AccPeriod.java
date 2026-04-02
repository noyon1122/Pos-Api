package com.pos.main.acc.entity;

import java.time.LocalDate;
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
@Table(name = "acc_period")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccPeriod {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_period_seq")
	@SequenceGenerator(name = "acc_period_seq", sequenceName = "ACC_PERIOD_SEQ", allocationSize = 1)
	private Long id;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "is_active")
    private Boolean isActive;

}
