package com.karlos.todolist.controller;

import com.karlos.todolist.entities.Task;
import com.karlos.todolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/todolist/a3")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/tasks")
    public Task createdTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskService.listAllTasks();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable(value = "id") Long id) {
    return taskService.findTaskById(id);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTaskById(@PathVariable(value = "id") Long id, @RequestBody Task task) {
        return taskService.updateTaskById(task, id);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Object> deleteTaskById(@PathVariable(value = "id") Long id) {
        return taskService.deleteTaskById(id);
    }

}

