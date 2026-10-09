package com.lifeos.student.repository;

import com.lifeos.student.entity.Task;
import com.lifeos.student.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByUser(User user);

}