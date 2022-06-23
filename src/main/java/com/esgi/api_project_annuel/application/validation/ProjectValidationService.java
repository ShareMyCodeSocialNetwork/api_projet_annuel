package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Project;

import java.util.Objects;

public class ProjectValidationService {
    private final UserValidationService userValidationService = new UserValidationService();
    public boolean isValid(Project project){
        if(project == null)
            return false;

        return userValidationService.isUserValid(project.getOwner()) &&
                !Objects.equals(project.getName(), "") &&
                project.getName() != null &&
                !Objects.equals(project.getDescription(), "") &&
                project.getDescription() != null;
    }
}
