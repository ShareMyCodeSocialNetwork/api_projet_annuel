package com.esgi.api_project_annuel.web.response;

public class RoleResponse {
    public int id;
    public String name;

    public RoleResponse() {}

    public int getId() {
        return id;
    }

    public RoleResponse setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoleResponse setName(String name) {
        this.name = name;
        return this;
    }
}
