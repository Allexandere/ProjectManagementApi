package io.ilya.epamjuniortask.service;

import io.ilya.epamjuniortask.entity.Task;
import io.ilya.epamjuniortask.entity.User;
import io.ilya.epamjuniortask.exception.ApiRequestException;
import io.ilya.epamjuniortask.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(@Autowired TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String title) {
        try {
            return taskRepository.save(new Task(title));
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }

    public Task getTask(long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> filter(String title) {
        return taskRepository.filter(title);
    }

    public Task deleteTask(Long taskId) {
        Task foundTask = tryFindTask(taskId);

        if(foundTask.getParentTask() != null)
            foundTask.getParentTask().getSubTasks().remove(foundTask);

        taskRepository.deleteById(taskId);

        return foundTask;
    }

    public Task createSubTask(Long taskId, String newSubTaskName) {
        Task foundTask = tryFindTask(taskId);

        if(newSubTaskName.isBlank() || newSubTaskName.isEmpty())
            throw new ApiRequestException("Invalid SubTaskName.");

        for (Task subTask : foundTask.getSubTasks())
            if (subTask.getTitle().equals(newSubTaskName))
                throw new ApiRequestException("Subtask with this title already exists.");

        if(foundTask.getTitle().equals(newSubTaskName))
            throw new ApiRequestException("Subtask name can't equals parent task's name.");

        Task newSubTask = new Task(newSubTaskName, foundTask.getEnrolledUser());

        newSubTask.assignParentTask(foundTask);

        return taskRepository.save(newSubTask);
    }

    public Set<Task> getAllSubTasks(Long taskId) {
        Task foundTask = tryFindTask(taskId);
        return foundTask.getSubTasks();
    }

    public User getAllEnrolledUser(Long taskId){
        Task foundTask = tryFindTask(taskId);
        return foundTask.getEnrolledUser();
    }

    public Task tryFindTask(Long taskId) {
        Optional<Task> foundTask = taskRepository.findById(taskId);
        if (foundTask.isEmpty())
            throw new ApiRequestException("Task with such id not found.");
        return foundTask.get();
    }

    public String makeReport(Long taskId, int n){
        Task foundTask = tryFindTask(taskId);
        StringBuilder report = new StringBuilder();
        report.append("\t".repeat(n)).append("Title: ").append(foundTask.getTitle()).append("\n");
        report.append("\t".repeat(n)).append("SubTasks enrolled: ").append(foundTask.getSubTasks().size()).append("\n");
        for (Task subTask : foundTask.getSubTasks())
            report.append(makeReport(subTask.getId(), n + 1));
        return report.toString();
    }
}
