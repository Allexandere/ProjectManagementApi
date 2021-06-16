package io.ilya.epamjuniortask.service;

import io.ilya.epamjuniortask.entity.Project;
import io.ilya.epamjuniortask.entity.User;
import io.ilya.epamjuniortask.exception.ApiRequestException;
import io.ilya.epamjuniortask.repository.ProjectRepository;
import io.ilya.epamjuniortask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public ProjectService(@Autowired ProjectRepository projectRepository,
                          @Autowired UserRepository userRepository,
                          @Autowired UserService userService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public Project createProject(String title) {
        try {
            Project project = new Project(title);
            return projectRepository.save(project);
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }

    public Project getProject(Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    public List<Project> filter(String title) {
        return projectRepository.filter(title);
    }

    public Project addUser(Long projectId, Long userId) {
        Project foundProject = tryFindProject(projectId);
        User foundUser = userService.tryFindUser(userId);

        if(foundUser.getEnrolledProject() != null)
            throw new ApiRequestException("User already have enrolled project.");

        for (User enrolledUser : foundProject.getEnrolledUsers())
            if (enrolledUser.getId().equals(userId))
                throw new ApiRequestException("Project already have user with such id.");

        foundUser.assignProject(foundProject);

        userRepository.save(foundUser);

        foundProject.getEnrolledUsers().add(foundUser);

        return foundProject;
    }

    public Project deleteProject(Long projectId){
        Project foundProject = tryFindProject(projectId);

        userRepository.deleteById(projectId);

        return foundProject;
    }

    private Project tryFindProject(Long projectId) {
        Optional<Project> foundProject = projectRepository.findById(projectId);
        if (foundProject.isEmpty())
            throw new ApiRequestException("Project with such id not found.");
        return foundProject.get();
    }

    public String makeReport(Long projectId, int n){
        Project foundProject = tryFindProject(projectId);
        StringBuilder report = new StringBuilder();
        report.append("\t".repeat(n)).append("Title: ").append(foundProject.getTitle()).append("\n");
        report.append("\t".repeat(n)).append("Users enrolled: ").append(foundProject.getEnrolledUsers().size()).append("\n");
        for (User enrolledUser : foundProject.getEnrolledUsers())
            report.append(userService.makeReport(enrolledUser.getId(), n + 1));
        return report.toString();
    }
}
