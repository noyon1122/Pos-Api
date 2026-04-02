package com.pos.main.remit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.main.remit.entity.RemitBank;



@Repository
public interface RemitBankRepository extends JpaRepository<RemitBank, Long> {

	Optional<RemitBank>findByBankName(String bankName);
}
