package com.esgi.api_project_annuel.web.response;

public class GroupResponse {
    public int id;
    public String name;
    public String description;

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

    public String getDescription() {
        return description;
    }

    public GroupResponse setDescription(String description) {
        this.description = description;
        return this;
    }
}
