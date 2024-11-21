package com.karlos.todolist;

import com.karlos.todolist.entities.Task;
import com.karlos.todolist.repositories.TaskRepository;
import com.karlos.todolist.services.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    public TaskServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTask() {
        // Arrange
        Task task = new Task("Testar o método criar tarefa", "usando o JUnit para fazer testes unitarios automatizados");
        when(taskRepository.save(task)).thenReturn(task);

        // Act
        ResponseEntity<String> response = taskService.createTask(task);

        // Assert
        assertEquals("Tarefa criada com sucesso!", response.getBody());
        assertEquals(200, response.getStatusCodeValue());

        // Verifica se o método save foi chamado corretamente
        verify(taskRepository).save(task);
    }

    @Test
    void updateTaskById_TaskFound_ReturnsUpdatedMessage() {
        // Arrange
        Long id = 1L;
        Task existingTask = new Task("Tarefa antiga", "descrição 1");
        existingTask.setId(id);

        Task updatedTask = new Task("Tarefa nova", "descrição 2");

        when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);

        // Act
        ResponseEntity<String> response = taskService.updateTaskById(updatedTask, id);

        // Assert
        assertEquals("Tarefa atualizada com sucesso!", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(taskRepository).findById(id);
        verify(taskRepository).save(existingTask);
    }

    @Test
    void updateTaskById_TaskNotFound_ReturnsNotFoundMessage() {
        // Arrange
        Long id = 1L;
        Task updatedTask = new Task("Tarefa nova", "descrição");

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = taskService.updateTaskById(updatedTask, id);

        // Assert
        assertEquals("Tarefa não encontrada!", response.getBody());
        assertEquals(404, response.getStatusCodeValue());
        verify(taskRepository).findById(id);
        verify(taskRepository, never()).save(any(Task.class));
    }
}
