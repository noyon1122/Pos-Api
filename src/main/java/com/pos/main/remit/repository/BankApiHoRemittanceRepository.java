package com.pos.main.remit.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.main.remit.entity.BankApiHoRemittance;




@Repository
public interface BankApiHoRemittanceRepository extends JpaRepository<BankApiHoRemittance, Long> {

	Boolean existsByRequestId(String requestId);
}
