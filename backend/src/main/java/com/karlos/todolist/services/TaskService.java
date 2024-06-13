package com.karlos.todolist.services;

import com.karlos.todolist.entities.Task;
import com.karlos.todolist.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public ResponseEntity<String> createTask (Task task) {
        taskRepository.save(task);
        return ResponseEntity.ok("Task created successfully!");
    }

    public List<Task> listAllTasks() {
        return taskRepository.findAll();
    }

    public ResponseEntity<Task> findTaskById (Long id) {
        return taskRepository.findById(id)
                .map(task -> ResponseEntity.ok().body(task))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<String> updateTaskById (Task task, Long id) {
        return taskRepository.findById(id).map(taskToUpdate -> {
            taskToUpdate.setTaskName(task.getTaskName());
            taskToUpdate.setDescription(task.getDescription());
            taskRepository.save(taskToUpdate);
            return ResponseEntity.ok("Task updated successfully!");
        }).orElse(ResponseEntity.status(404).body("Task not found!"));
    }

    public ResponseEntity<String> deleteTaskById (Long id) {
        return taskRepository.findById(id)
                .map(taskToDelete -> {
                    taskRepository.deleteById(id);
                    return ResponseEntity.ok("Task deleted successfully!");
                }).orElse(ResponseEntity.status(404).body("Task not found!"));
    }

}
