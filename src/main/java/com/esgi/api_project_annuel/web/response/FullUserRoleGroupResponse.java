package com.esgi.api_project_annuel.web.response;

import com.esgi.api_project_annuel.Domain.entities.UserRoleGroup;

import java.util.List;

public class FullUserRoleGroupResponse {
    private List<UserRoleGroup> userInGroupWithRole;
    private UserRoleGroup isInGroup;

    public List<UserRoleGroup> getUserInGroupWithRole() {
        return userInGroupWithRole;
    }

    public FullUserRoleGroupResponse setUserInGroupWithRole(List<UserRoleGroup> userInGroupWithRole) {
        this.userInGroupWithRole = userInGroupWithRole;
        return this;
    }

    public UserRoleGroup getIsInGroup() {
        return isInGroup;
    }

    public FullUserRoleGroupResponse setIsInGroup(UserRoleGroup isInGroup) {
        this.isInGroup = isInGroup;
        return this;
    }
}
