package com.lifeos.student.repository;

import com.lifeos.student.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByCollegeEmail(String collegeEmail);

    Optional<User> findByHallTicketNumber(String hallTicketNumber);

    boolean existsByCollegeEmail(String collegeEmail);

    boolean existsByHallTicketNumber(String hallTicketNumber);
}