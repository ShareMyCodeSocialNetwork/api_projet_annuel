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
    ProjectRepository projectRepository;

    @Autowired
    CodeCommand codeCommand;

    @Autowired
    GroupQuery groupQuery;


    ProjectValidationService projectValidationService = new ProjectValidationService();
    GroupValidationService groupValidationService = new GroupValidationService();

    public Project create(ProjectRequest projectRequest, User user, Group group){
        Project project = new Project();
        project.setOwner(user);
        project.setName(projectRequest.name);
        project.setDescription(projectRequest.description);

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
            codeCommand.setProjectToNull(project.get());
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
        Optional<List<Project>> projects = Optional.ofNullable(projectRepository.findAllByOwner(user));
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
            if(project.get().getOwner().getId() != owner.getId()){
                project.get().setOwner(owner);
                if(projectValidationService.isValid(project.get()))
                    return projectRepository.save(project.get());
            }
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
    public Project changeDescription(int id, ProjectRequest projectRequest){
        Optional<Project> project = Optional.ofNullable(projectRepository.findById(id));
        if(project.isPresent()){
            project.get().setDescription(projectRequest.description);
            if(projectValidationService.isValid(project.get()))
                return projectRepository.save(project.get());
        }
        return null;
    }
}
