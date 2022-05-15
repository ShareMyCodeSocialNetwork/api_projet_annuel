package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.Domain.entities.Project;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectQuery {
    @Autowired
    ProjectRepository projectRepository;

    public ProjectQuery(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAll(){
        return projectRepository.findAll();
    }

    public Project getById(int id){
        return projectRepository.findById(id);
    }

    public List<Project> getByName(String name) {
        return projectRepository.findAllByName(name);
    }

    public List<Project> getByOwner(User user) {
        return projectRepository.findAllByOwner(user);
    }

    public List<Project> getByGroup(Group group) {
        return projectRepository.findAllByGroup(group);
    }
}
