package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Project;
import com.esgi.api_project_annuel.application.command.ProjectCommand;
import com.esgi.api_project_annuel.application.query.GroupQuery;
import com.esgi.api_project_annuel.application.query.ProjectQuery;
import com.esgi.api_project_annuel.application.query.UserQuery;
import com.esgi.api_project_annuel.web.request.ProjectRequest;
import com.esgi.api_project_annuel.web.response.ProjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectCommand projectCommand;

    @Autowired
    ProjectQuery projectQuery;
    @Autowired
    UserQuery userQuery;
    @Autowired
    GroupQuery groupQuery;

    public ProjectController(ProjectCommand projectCommand, ProjectQuery projectQuery) {
        this.projectCommand = projectCommand;
        this.projectQuery = projectQuery;
    }

    @RequestMapping("/create")
    public ResponseEntity<ProjectResponse> addProject(@RequestBody ProjectRequest projectRequest){
        var user = userQuery.getById(projectRequest.user_id);
        var group = groupQuery.getById(projectRequest.group_id);
        var project = projectCommand.create(projectRequest, user, group);
        if(project == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(projectToProjectResponse(project), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProjectResponse>> getAll(){
        return new ResponseEntity<>(
                this.listProjectToListProjectResponse(projectQuery.getAll()),
                HttpStatus.OK);
    }

    //to test
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable int projectId){
        var project = projectQuery.getById(projectId);
        if(project == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(projectToProjectResponse(
                project),
                HttpStatus.OK
        );
    }

    @GetMapping("/name/{projectName}")
    public ResponseEntity<List<ProjectResponse>> getProjectByName(@PathVariable String projectName){
        var project = projectQuery.getByName(projectName);
        return new ResponseEntity<>(listProjectToListProjectResponse(
                project),
                HttpStatus.OK
        );
    }
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ProjectResponse>> getProjectByGroup(@PathVariable int groupId){
        var group = groupQuery.getById(groupId);
        var project = projectQuery.getByGroup(group);
        return new ResponseEntity<>(listProjectToListProjectResponse(
                project),
                HttpStatus.OK
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectResponse>> getProjectByOwner(@PathVariable int userId){
        var user = userQuery.getById(userId);
        var project = projectQuery.getByOwner(user);
        return new ResponseEntity<>(listProjectToListProjectResponse(
                project),
                HttpStatus.OK
        );
    }

    @PatchMapping("/group/{projectId}")
    public ResponseEntity<ProjectResponse> changeGroup(@PathVariable int projectId,@RequestBody ProjectRequest projectRequest){
        var group = groupQuery.getById(projectRequest.group_id);
        var project = projectCommand.changeGroup(projectId,group);
        if(project == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(projectToProjectResponse(project), HttpStatus.OK);
    }

    @PatchMapping("/owner/{projectId}")
    public ResponseEntity<ProjectResponse> changeOwner(@PathVariable int projectId,@RequestBody ProjectRequest projectRequest){
        var owner = userQuery.getById(projectRequest.user_id);
        var project = projectCommand.changeOwner(projectId,owner);
        if(project == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(projectToProjectResponse(project), HttpStatus.OK);
    }

    @PatchMapping("/{projectId}/name")
    public ResponseEntity<ProjectResponse> changeName(@PathVariable int projectId,@RequestBody ProjectRequest projectRequest){
        var project = projectCommand.changeName(projectId,projectRequest);
        if(project == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(projectToProjectResponse(project), HttpStatus.OK);
    }


    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable int projectId){
        var project = projectQuery.getById(projectId);
        if(project == null)
            return new ResponseEntity<>(
                    "Project " + projectId + " not exist",
                    HttpStatus.BAD_REQUEST
            );
        projectCommand.delete(projectId);
        return new ResponseEntity<>(
                "Project " + projectId + " deleted",
                HttpStatus.BAD_REQUEST
        );
    }






    private ProjectResponse projectToProjectResponse(Project project){
        return new ProjectResponse()
                .setId(project.getId())
                .setName(project.getName())
                .setGroup(project.getGroup())
                .setUser(project.getOwner());
    }

    private List<ProjectResponse> listProjectToListProjectResponse(List<Project> projects){
        List<ProjectResponse> projectResponses = new ArrayList<>();
        projects.forEach(project -> projectResponses.add(this.projectToProjectResponse(project)));
        return projectResponses;
    }
    
}
