package com.esgi.api_project_annuel.web.response;

public class RoleResponse {
    int id;
    String name;

    public RoleResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
