package com.esgi.api_project_annuel.web.response;

import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.Domain.entities.User;

public class ProjectResponse {
    public int id;
    public String name;
    public User user;
    public Group group;

    public ProjectResponse() {
    }

    public int getId() {
        return id;
    }

    public ProjectResponse setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProjectResponse setName(String name) {
        this.name = name;
        return this;
    }

    public User getUser() {
        return user;
    }

    public ProjectResponse setUser(User user) {
        this.user = user;
        return this;
    }

    public Group getGroup() {
        return group;
    }

    public ProjectResponse setGroup(Group group) {
        this.group = group;
        return this;
    }
}
