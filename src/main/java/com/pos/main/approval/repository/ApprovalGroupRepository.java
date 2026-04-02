package com.pos.main.approval.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.main.approval.entity.ApprovalGroup;

@Repository
public interface ApprovalGroupRepository extends JpaRepository<ApprovalGroup, Long> {

	Optional<ApprovalGroup>findByKeyword(String keyword);
	List<ApprovalGroup> findByKeywordIn(List<String> keywords);
}
