package io.ilya.epamjuniortask.controller;

import io.ilya.epamjuniortask.entity.Task;
import io.ilya.epamjuniortask.entity.User;
import io.ilya.epamjuniortask.exception.ApiRequestException;
import io.ilya.epamjuniortask.service.TaskService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    public TaskController(@Autowired TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation(value = "Creates task with specified name")
    @PostMapping("/task/{title}")
    public Task createTask(@PathVariable(name = "title") String title) {
        System.out.println("Creating new task with title " + title);
        Task createdTask = taskService.createTask(title);
        if(createdTask != null)
            return createdTask;
        else
            throw new ApiRequestException("Task with this name already exists.");
    }

    @ApiOperation(value = "Gets all task|s with specified searchword or id")
    @GetMapping("/task")
    public List<Task> getTasks(@RequestParam(name = "id", required = false) Long taskId,
                               @RequestParam(name = "title", required = false) String title) {
        if (taskId != null)
            return Collections.singletonList(taskService.getTask(taskId));
        else{
            String searchWord = (title == null) ? "" : title;
            return taskService.filter(searchWord);
        }
    }

    @ApiOperation(value = "Returns enrolled user of task")
    @GetMapping("/task/{taskId}/user")
    public User getAllEnrolledUsers(@PathVariable(name = "taskId") Long taskId){
        return taskService.getAllEnrolledUser(taskId);
    }

    @ApiOperation(value = "Returns subtasks of task")
    @GetMapping("task/{taskId}/subtasks")
    public Set<Task> getAllSubTasks(@PathVariable(name = "taskId") Long taskId){
        return taskService.getAllSubTasks(taskId);
    }

    @ApiOperation(value = "Generates report of task")
    @GetMapping("/task/{taskId}/report")
    public String makeTaskReport(@PathVariable(name = "taskId") Long taskId){
        return taskService.makeReport(taskId, 0);
    }

    @ApiOperation(value = "Deletes task with specified id")
    @DeleteMapping("/task/{taskId}")
    public Task deleteTask(@PathVariable(name = "taskId") Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @ApiOperation(value = "Creates subtask and assigns it to an existed task with speccified id")
    @PutMapping("/task")
    public Task createSubTask(@RequestParam(name = "taskId") Long taskId,
                              @RequestParam(name = "subTaskName") String subTaskName){
        return taskService.createSubTask(taskId, subTaskName);
    }
}
