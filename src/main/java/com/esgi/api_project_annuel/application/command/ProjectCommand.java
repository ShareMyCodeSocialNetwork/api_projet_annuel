package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.Domain.entities.Project;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.ProjectRepository;
import com.esgi.api_project_annuel.application.query.GroupQuery;
import com.esgi.api_project_annuel.application.validation.GroupValidationService;
import com.esgi.api_project_annuel.application.validation.ProjectValidationService;
import com.esgi.api_project_annuel.web.request.ProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectCommand {
    @Autowired
    ProjectCommand projectCommand;
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    GroupQuery groupQuery;


    ProjectValidationService projectValidationService = new ProjectValidationService();
    GroupValidationService groupValidationService = new GroupValidationService();

    public Project create(ProjectRequest projectRequest, User user, Group group){
        Project project = new Project();
        project.setOwner(user);
        project.setName(projectRequest.name);

        //si group pas valide, ca veux dire que le projet n'est pas dans un groupe
        if(groupValidationService.isValid(group))
            project.setGroup(group);


        if(projectValidationService.isValid(project))
                return projectRepository.save(project);
        return null;
    }

    public void delete(int projectId){
        Optional<Project> project = Optional.ofNullable(projectRepository.findById(projectId));
        project.ifPresent(project1 -> {
            project.get().setGroup(null);
            project.get().setOwner(null);
            projectRepository.save(project.get());
            projectRepository.delete(project.get());
        });


    }

    public void deleteAllProjectsGroup(Group group){
        Optional<List<Project>> projects = Optional.ofNullable(projectRepository.findAllByGroup(group));
        projects.ifPresent(projectList ->
                projectList.forEach(project ->
                        delete(project.getId())
                )
        );

    }

    public void deleteAllProjectsUser(User user){
        Optional<List<Project>> projects = Optional.ofNullable(projectRepository.findAllByUser(user));
        projects.ifPresent(projectList ->
                projectList.forEach(project ->
                        delete(project.getId())
                )
        );
    }

    public Project changeGroup(int id, Group group){
        Optional<Project> project = Optional.ofNullable(projectRepository.findById(id));
        if(groupValidationService.isValid(group) && project.isPresent()){
            project.get().setGroup(group);
            if(projectValidationService.isValid(project.get()))
                return projectRepository.save(project.get());
        }
        return null;
    }

    public Project changeOwner(int id, User owner){
        Optional<Project> project = Optional.ofNullable(projectRepository.findById(id));
        if(project.isPresent()){
            project.get().setOwner(owner);
            if(projectValidationService.isValid(project.get()))
                return projectRepository.save(project.get());
        }
        return null;
    }

    public Project changeName(int id, ProjectRequest projectRequest){
        Optional<Project> project = Optional.ofNullable(projectRepository.findById(id));
        if(project.isPresent()){
            project.get().setName(projectRequest.name);
            if(projectValidationService.isValid(project.get()))
                return projectRepository.save(project.get());
        }
        return null;
    }
}
