package com.pos.main.api.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.scheduling.support.SimpleTriggerContext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "api_client_ips")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiClientIp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // api_users 
    @JoinColumn(name = "api_user_id", nullable = false)
    private ApiUsers apiUser;

    @Column(nullable = false)
    private String ipAddress; 

    private Boolean isActive = true;
    
    private String description;
    private String type;
    private String createdBy;
    private LocalDateTime created;
}
