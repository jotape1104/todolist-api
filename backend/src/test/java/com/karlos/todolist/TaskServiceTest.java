package com.karlos.todolist;

import com.karlos.todolist.entities.Task;
import com.karlos.todolist.repositories.TaskRepository;
import com.karlos.todolist.services.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void deleteTaskById_Success() {
        // Configurar o cenário
        Long taskId = 1L;
        Task existingTask = new Task("Tarefa exemplo", "Descrição exemplo");
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        // Executar o método
        ResponseEntity<String> response = taskService.deleteTaskById(taskId);

        // Verificar o resultado
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Tarefa excluída com sucesso!", response.getBody());

        // Verificar interações com o repositório
        verify(taskRepository).findById(taskId);
        verify(taskRepository).deleteById(taskId);
    }

    @Test
    void deleteTaskById_NotFound() {
        // Configurar o cenário
        Long taskId = 2L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Executar o método
        ResponseEntity<String> response = taskService.deleteTaskById(taskId);

        // Verificar o resultado
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Tarefa não encontrada!", response.getBody());

        // Verificar interações com o repositório
        verify(taskRepository).findById(taskId);
        verify(taskRepository, never()).deleteById(anyLong());
    }

    @Test
    void testListAllTasks() {
        // Configuração do mock para retornar uma lista de tarefas
        List<Task> tasks = Arrays.asList(
                new Task("Tarefa 1", "Descrição 1"),
                new Task("Tarefa 2", "Descrição 2")
        );
        when(taskRepository.findAll()).thenReturn(tasks);

        // Execução do método
        List<Task> result = taskService.listAllTasks();

        // Verificações
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Tarefa 1", result.get(0).getTaskName());
        assertEquals("Tarefa 2", result.get(1).getTaskName());

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testFindTaskByIdFound() {
        // Configuração do mock para encontrar uma tarefa pelo ID
        Task task = new Task("Tarefa Encontrada", "Descrição");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Execução do método
        ResponseEntity<Task> response = taskService.findTaskById(1L);

        // Verificações
        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Tarefa Encontrada", response.getBody().getTaskName());

        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testFindTaskByIdNotFound() {
        // Configuração do mock para retornar um Optional vazio
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Execução do método
        ResponseEntity<Task> response = taskService.findTaskById(1L);

        // Verificações
        assertNotNull(response);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertNull(response.getBody());

        verify(taskRepository, times(1)).findById(1L);
    }

}
