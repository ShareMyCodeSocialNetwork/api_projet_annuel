package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.UserRoleGroup;

public class UserRoleGroupValidationService {

    private final UserValidationService userValidationService = new UserValidationService();
    private final GroupValidationService groupValidationService = new GroupValidationService();
    private final RoleValidationService roleValidationService = new RoleValidationService();

    public boolean isValid(UserRoleGroup userRoleGroup){
        if(userRoleGroup == null)
            return false;
        return userValidationService.isUserValid(userRoleGroup.getUser()) &&
                groupValidationService.isValid(userRoleGroup.getGroup()) &&
                roleValidationService.isValid(userRoleGroup.getRole());
    }
}
