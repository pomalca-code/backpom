package com.pomalca.task.service;

import com.pomalca.task.models.SharedTask;

import java.util.List;

public interface SharedTaskService {
    List<SharedTask> getSharedTasksByTaskId(Long taskId);
    SharedTask createSharedTask(SharedTask sharedTask);
    boolean deleteSharedTask(Long id);
}
