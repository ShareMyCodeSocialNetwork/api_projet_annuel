package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Group;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class GroupValidationService {



    public GroupValidationService(){

    }

    @JsonIgnore
    public boolean isValid(Group group) {
        if(group == null)
            return false;
        return !group.getName().equals("") && !group.getName().isBlank() &&
                !group.getDescription().equals("") && !group.getDescription().isBlank();
    }
}
