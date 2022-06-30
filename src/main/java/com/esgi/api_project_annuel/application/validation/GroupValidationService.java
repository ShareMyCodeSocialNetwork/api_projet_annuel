package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Group;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class GroupValidationService {

    private final UserValidationService userValidationService = new UserValidationService();

    @JsonIgnore
    public boolean isValid(Group group) {
        if(group == null)
            return false;
        if (!userValidationService.isUserValid(group.getOwner()))
            return false;
        return !group.getName().equals("") && !group.getName().isBlank() &&
                !group.getDescription().equals("") && !group.getDescription().isBlank();
    }
}
