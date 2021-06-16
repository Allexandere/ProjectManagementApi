package io.ilya.epamjuniortask.service;

import io.ilya.epamjuniortask.entity.Task;
import io.ilya.epamjuniortask.entity.User;
import io.ilya.epamjuniortask.exception.ApiRequestException;
import io.ilya.epamjuniortask.repository.TaskRepository;
import io.ilya.epamjuniortask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    public UserService(@Autowired UserRepository userRepository,
                       @Autowired TaskRepository taskRepository,
                       @Autowired TaskService taskService) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    public User createUser(String name) {
        try {
            User user = new User(name);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> filter(String name) {
        return userRepository.filter(name);
    }

    public User addTask(Long userId, Long taskId) {
        User foundUser = tryFindUser(userId);
        Task foundTask = taskService.tryFindTask(taskId);

        if(foundTask.getEnrolledUser() != null)
            throw new ApiRequestException("Task already have enrolled user.");

        if(foundTask.getParentTask() != null)
            throw new ApiRequestException("Can't enroll to subtask of some task.");

        for (Task enrolledTask : foundUser.getEnrolledTasks())
            if (enrolledTask.getId().equals(taskId))
                throw new ApiRequestException("User already have tusk with such id.");

        foundTask.assignEnrolledUser(foundUser);

        taskRepository.save(foundTask);

        foundUser.getEnrolledTasks().add(foundTask);

        return foundUser;
    }

    public User deleteUser(Long userId){
        User foundUser = tryFindUser(userId);

        userRepository.deleteById(userId);

        return foundUser;
    }

    public User tryFindUser(Long userId) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty())
            throw new ApiRequestException("User with such id not found.");
        return foundUser.get();
    }

    public String makeReport(long userId, int n){
        User foundUser = tryFindUser(userId);
        StringBuilder report = new StringBuilder();
        report.append("\t".repeat(n)).append("\tName: ").append(foundUser.getName()).append("\n");
        report.append("\t".repeat(n)).append("\tTasks enrolled: ").append(foundUser.getEnrolledTasks().size()).append("\n");
        for (Task enrolledTask : foundUser.getEnrolledTasks())
            report.append(taskService.makeReport(enrolledTask.getId(), n + 1));
        return report.toString();
    }
}
