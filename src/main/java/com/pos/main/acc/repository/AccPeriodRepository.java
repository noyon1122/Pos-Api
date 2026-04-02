package com.pos.main.acc.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.main.acc.entity.AccPeriod;

@Repository
public interface AccPeriodRepository extends JpaRepository<AccPeriod, Long> {
//	@Query("SELECT a FROM AccPeriod a " +
//		       "WHERE a.startDate <= :date " +
//		       "AND a.endDate >= :date " +
//		       "ORDER BY a.startDate DESC")
//	Optional<AccPeriod> findActivePeriod(@Param("date") LocalDate date);
	
	Optional<AccPeriod>
	findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateDesc(
	        LocalDate date1,
	        LocalDate date2
	);


}
