package io.ilya.epamjuniortask.controller;

import io.ilya.epamjuniortask.entity.Project;
import io.ilya.epamjuniortask.exception.ApiRequestException;
import io.ilya.epamjuniortask.service.ProjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(@Autowired ProjectService projectService) {
        this.projectService = projectService;
    }

    @ApiOperation(value = "Creates project with specified title")
    @PostMapping("/project/{title}")
    public Project createProject(@PathVariable(name = "title") String title) {
        Project createdProject = projectService.createProject(title);
        if (createdProject != null)
            return createdProject;
        else
            throw new ApiRequestException("Project with this name already exists.");
    }

    @ApiOperation(value = "Gets all project|s with specified searchword or id")
    @GetMapping("/project")
    public List<Project> getUsers(@RequestParam(name = "id", required = false) Long projectId,
                                  @RequestParam(name = "title", required = false) String title) {
        if (projectId != null)
            return Collections.singletonList(projectService.getProject(projectId));
        else {
            String searchWord = (title == null) ? "" : title;
            return projectService.filter(searchWord);
        }
    }

    @ApiOperation(value = "Generates report of project")
    @GetMapping("/project/{projectId}/report")
    public String makeProjectReport(@PathVariable(name = "projectId") Long projectId){
        return projectService.makeReport(projectId, 0);
    }

    @ApiOperation(value = "Adds user to project")
    @PutMapping("/project")
    public Project addUserToProject(@RequestParam(name = "projectId") Long projectId,
                                    @RequestParam(name = "userId") Long userId) {
        return projectService.addUser(projectId, userId);
    }

    @ApiOperation(value = "Deletes project with specified id")
    @DeleteMapping("/project/{projectId}")
    public Project deleteProject(@PathVariable(name = "projectId") Long projectId) {
        return projectService.deleteProject(projectId);
    }
}
