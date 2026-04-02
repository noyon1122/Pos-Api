package com.pos.main.approval.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.main.approval.entity.ApprovalStatus;



@Repository
public interface ApprovalStatusRepository extends JpaRepository<ApprovalStatus, Long> {

	Optional<ApprovalStatus>findByKeyword(String keyword);
	List<ApprovalStatus> findByKeywordIn(List<String> keywords);
}
