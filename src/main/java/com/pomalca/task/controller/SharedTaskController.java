package com.pomalca.task.controller;


import com.pomalca.task.models.SharedTask;
import com.pomalca.task.service.SharedTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shared-tasks")
public class SharedTaskController {

    @Autowired
    private SharedTaskService sharedTaskService;

    @GetMapping("/{taskId}")
    public ResponseEntity<List<SharedTask>> getSharedTasksByTaskId(@PathVariable("taskId") Long taskId) {
        List<SharedTask> sharedTasks = sharedTaskService.getSharedTasksByTaskId(taskId);
        if (sharedTasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sharedTasks);
    }

    @PostMapping
    public ResponseEntity<SharedTask> createSharedTask(@RequestBody SharedTask sharedTask) {
        // Verifica que 'sharedTask.getSharedWith()' no sea nulo
        if (sharedTask.getSharedWith() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        SharedTask createdSharedTask = sharedTaskService.createSharedTask(sharedTask);
        return ResponseEntity.ok(createdSharedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSharedTask(@PathVariable("id") Long id) {
        boolean isRemoved = sharedTaskService.deleteSharedTask(id);
        if (!isRemoved) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
