package com.pos.main.clearing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.main.clearing.entity.PosMachine;
import com.pos.main.pos.entity.Plazas;
import com.pos.main.remit.entity.RemitBank;

@Repository
public interface PosMachineRepository extends JpaRepository<PosMachine, Long> {

	Optional<PosMachine>findByPlazaAndRemitBank(Plazas plaza,RemitBank remitBank);
}
