package com.esgi.api_project_annuel.web.response;

import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.Domain.entities.Role;
import com.esgi.api_project_annuel.Domain.entities.User;

public class UserRoleGroupResponse {
    public int id;
    public User user;
    public Role role;
    public Group group;

    public UserRoleGroupResponse() {
    }

    public int getId() {
        return id;
    }

    public UserRoleGroupResponse setId(int id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserRoleGroupResponse setUser(User user) {
        this.user = user;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public UserRoleGroupResponse setRole(Role role) {
        this.role = role;
        return this;
    }

    public Group getGroup() {
        return group;
    }

    public UserRoleGroupResponse setGroup(Group group) {
        this.group = group;
        return this;
    }
}
