package com.esgi.api_project_annuel.web.response;

public class GroupResponse {
    public int id;
    public String name;

    public GroupResponse() {
    }

    public int getId() {
        return id;
    }

    public GroupResponse setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public GroupResponse setName(String name) {
        this.name = name;
        return this;
    }
}
