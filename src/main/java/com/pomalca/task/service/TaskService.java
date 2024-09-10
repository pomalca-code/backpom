package com.pomalca.task.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TaskService {

    @Transactional
    List<Map<String, Object>> getTasksForUser(Long user_id);
    @Transactional
    List<Map<String, Object>> getTasksSharedWithUser(Long shared_with_id);
    @Transactional
    List<Map<String, Object>> getSharedTasksByTaskId(Long taskId);
}
