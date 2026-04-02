package com.pos.main.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.main.api.entity.ApiUsers;

@Repository
public interface ApiUserRepository extends JpaRepository<ApiUsers, Long> {

	Optional<ApiUsers> findByUsername(String username);
}
