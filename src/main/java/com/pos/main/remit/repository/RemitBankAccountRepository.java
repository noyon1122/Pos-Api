package com.pos.main.remit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.main.remit.entity.RemitBankAccount;



@Repository
public interface RemitBankAccountRepository extends JpaRepository<RemitBankAccount, Long> {

	Optional<RemitBankAccount>findByAccountNo(String accountNo);
}
