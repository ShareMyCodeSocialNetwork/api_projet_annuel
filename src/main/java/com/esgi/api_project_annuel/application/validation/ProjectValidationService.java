package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Project;

import java.util.Objects;

public class ProjectValidationService {

    private final UserValidationService userValidationService = new UserValidationService();
    private final GroupValidationService groupValidationService = new GroupValidationService();


    public boolean isValid(Project project){
        if(project == null)
            return false;

        return !userValidationService.isUserValid(project.getUser()) &&
                !groupValidationService.isValid(project.getGroup()) &&
                !Objects.equals(project.getName(), "") &&
                project.getName() != null;
    }
}
