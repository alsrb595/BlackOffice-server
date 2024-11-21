package org.example.blackoffice.service;

import org.example.blackoffice.dto.Taskdto;
import org.example.blackoffice.exception.ResourceNotFoundException;
import org.example.blackoffice.model.Task;
import org.example.blackoffice.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Taskdto> getAllTasks(Long memberId) {
        List<Task> tasks = taskRepository.findByMemberId(memberId);
        return tasks.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Taskdto createTask(Task task) {
        Task savedTask = taskRepository.save(task);
        return convertToDto(savedTask);
    }

    public Taskdto updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        // Task name이 null이 아니고 변경되었을 때만 업데이트
        if (updatedTask.getTaskName() != null && !updatedTask.getTaskName().equals(task.getTaskName())) {
            task.setTaskName(updatedTask.getTaskName());
        }

        // Due date가 null이 아니고 변경되었을 때만 업데이트
        if (updatedTask.getDueDate() != null && !updatedTask.getDueDate().equals(task.getDueDate())) {
            task.setDueDate(updatedTask.getDueDate());
        }

        // Status가 null이 아니고 변경되었을 때만 업데이트
        if (updatedTask.getStatus() != null && !updatedTask.getStatus().equals(task.getStatus())) {
            task.setStatus(updatedTask.getStatus());
        }
        Task savedTask = taskRepository.save(task);
        return convertToDto(savedTask);
    }

    private Taskdto convertToDto(Task task) {
        Taskdto taskDto = new Taskdto();
        taskDto.setId(task.getId());
        taskDto.setTaskName(task.getTaskName());

        // LocalDate를 String으로 변환
        if (task.getDueDate() != null) {
            taskDto.setDueDate(task.getDueDate().format(formatter));  // LocalDate -> String 변환
        }

        taskDto.setStatus(task.getStatus());
        return taskDto;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
