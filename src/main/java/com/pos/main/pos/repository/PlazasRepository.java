package com.pos.main.pos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.main.pos.entity.Plazas;



@Repository
public interface PlazasRepository extends JpaRepository<Plazas, Integer> {

	Optional<Plazas>findByVamCode(String vamCode);
}
