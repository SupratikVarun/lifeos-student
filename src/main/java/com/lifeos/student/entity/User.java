package com.lifeos.student.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String collegeEmail;

    @Column(nullable = false, unique = true)
    private String hallTicketNumber;

    @Column(nullable = false)
    private String college;

    @Column(nullable = false)
    private String branch;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String password;

    private String profileImage;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 500)
    private String bio;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}