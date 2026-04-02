package com.pos.main.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pos.main.api.entity.ApiClientIp;

@Repository
public interface ApiClientIpRepository extends JpaRepository<ApiClientIp, Long> {

	boolean existsByApiUser_UsernameAndIpAddressAndIsActiveTrue(String username, String ipAddress);
}
