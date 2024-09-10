package com.pomalca.task.repository;

import com.pomalca.task.models.SharedTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SharedTaskRepository extends JpaRepository<SharedTask, Long> {
    List<SharedTask> findByTaskId(Long taskId);
}
