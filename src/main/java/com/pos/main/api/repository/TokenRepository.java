package com.pos.main.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pos.main.api.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

	@Query("SELECT t FROM Token t INNER JOIN ApiUsers u ON t.user.id = u.id " +
	           "WHERE u.id = :userId AND (t.expired = false OR t.revoked = false)")
	    List<Token> findAllValidTokensByUser(Long userId);
	
	Optional<Token> findByAccessToken(String token);
	Optional<Token> findByRefreshToken(String token);
	
}
