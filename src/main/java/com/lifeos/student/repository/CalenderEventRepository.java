package com.lifeos.student.repository;

import com.lifeos.student.entity.CalenderEvent;
import com.lifeos.student.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CalenderEventRepository extends JpaRepository<CalenderEvent, UUID> {

    List<CalenderEvent> findByUser(User user);

}