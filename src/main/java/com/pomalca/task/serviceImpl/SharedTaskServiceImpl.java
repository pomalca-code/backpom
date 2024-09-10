package com.pomalca.task.serviceImpl;

import com.pomalca.task.models.SharedTask;
import com.pomalca.task.repository.SharedTaskRepository;
import com.pomalca.task.service.SharedTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SharedTaskServiceImpl implements SharedTaskService {

    @Autowired
    private SharedTaskRepository sharedTaskRepository;

    @Override
    public List<SharedTask> getSharedTasksByTaskId(Long taskId) {
        return sharedTaskRepository.findByTaskId(taskId);
    }

    @Override
    public SharedTask createSharedTask(SharedTask sharedTask) {
        // Verifica que 'sharedTask.getSharedWith()' no sea nulo
        if (sharedTask.getSharedWith() == null) {
            throw new IllegalArgumentException("sharedWith cannot be null");
        }
        return sharedTaskRepository.save(sharedTask);
    }

    @Override
    public boolean deleteSharedTask(Long id) {
        if (sharedTaskRepository.existsById(id)) {
            sharedTaskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
