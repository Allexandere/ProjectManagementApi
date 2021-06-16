package io.ilya.epamjuniortask.controller;

import io.ilya.epamjuniortask.entity.User;
import io.ilya.epamjuniortask.exception.ApiRequestException;
import io.ilya.epamjuniortask.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Creates user with specified name")
    @PostMapping("/user/{name}")
    public User createUser(@PathVariable(name = "name") String name) {
        User createdUser = userService.createUser(name);
        if (createdUser != null)
            return createdUser;
        else
            throw new ApiRequestException("User with this name already exists.");
    }

    @ApiOperation(value = "Gets all user|s with specified searchword or id")
    @GetMapping("/user")
    public List<User> getUsers(@RequestParam(name = "id", required = false) Long userId,
                               @RequestParam(name = "name", required = false) String name) {
        if (userId != null)
            return Collections.singletonList(userService.getUser(userId));
        else {
            String searchWord = (name == null) ? "" : name;
            return userService.filter(searchWord);
        }
    }

    @ApiOperation(value = "Generates report of user")
    @GetMapping("/user/{userId}/report")
    public String makeUserReport(@PathVariable(name = "userId") Long userId){
        return userService.makeReport(userId, 0);
    }

    @ApiOperation(value = "Assigns task to user")
    @PutMapping("/user")
    public User addTaskToUser(@RequestParam(name = "userId") Long userId,
                              @RequestParam(name = "taskId") Long taskId) {
        return userService.addTask(userId, taskId);
    }

    @ApiOperation(value = "Deletes user with specified id")
    @DeleteMapping("/user/{userId}")
    public User deleteUser(@PathVariable(name = "userId") Long userId) {
        return userService.deleteUser(userId);
    }
}
