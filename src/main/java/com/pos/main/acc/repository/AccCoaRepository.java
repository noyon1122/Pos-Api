package com.pos.main.acc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.main.acc.entity.AccCoa;

@Repository
public interface AccCoaRepository extends JpaRepository<AccCoa, Long> {

	Optional<AccCoa>findByKeyword(String keyword);
	List<AccCoa> findByKeywordIn(List<String> keywords);
}
