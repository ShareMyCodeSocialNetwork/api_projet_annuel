package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class RoleValidationService {
    public RoleValidationService() {
    }

    @JsonIgnore
    public boolean isValid(Role role){
        if(role == null)
            return false;
        return !Objects.equals(role.getName(), "") && !role.getName().isBlank();
    }
}
