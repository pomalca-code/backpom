package com.pomalca.task.controller;

import com.pomalca.task.models.Task;
import com.pomalca.task.models.User;
import com.pomalca.task.repository.TaskRepository;
import com.pomalca.task.repository.UserRepository;
import com.pomalca.task.serviceImpl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskServiceImpl taskServiceImpl;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        User user = userRepository.findById(task.getUser().getId()).orElseThrow();
        task.setUser(user);
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.ok(savedTask);
    }

    //GET USANDO SP
    //OBTENER LAS TASKS QUE CREO EL USER O QUE LE FUERON COMPARTIDAS
    @GetMapping("/user/{user_id}")
    public List<Map<String, Object>> getTasksForUser(@PathVariable Long user_id) {
        return taskServiceImpl.getTasksForUser(user_id);
    }

    //GET USANDO SP
    //OBTENER LAS TASKS QUE FUERON COMPARTIDAS CON EL USUARIO IDENTIFICADO CON "shared_with_id"
    @GetMapping("/shared-with/{shared_with_id}")
    public List<Map<String, Object>> getTasksSharedWithUser(@PathVariable("shared_with_id") Long shared_with_id) {
        return taskServiceImpl.getTasksSharedWithUser(shared_with_id);
    }

    @GetMapping("/shared_tasks/task/{task_id}")
    public List<Map<String, Object>> getSharedTasksByTaskId(@PathVariable Long task_id) {
        return taskServiceImpl.getSharedTasksByTaskId(task_id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            return ResponseEntity.ok(task.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            Task updatedTask = task.get();
            updatedTask.setTitle(taskDetails.getTitle());
            updatedTask.setCompleted(taskDetails.getCompleted());
            taskRepository.save(updatedTask);
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
