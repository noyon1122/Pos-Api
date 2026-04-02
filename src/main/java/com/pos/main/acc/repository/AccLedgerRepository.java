package com.pos.main.acc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.main.acc.entity.AccLedger;

@Repository
public interface AccLedgerRepository extends JpaRepository<AccLedger, Long> {

}
