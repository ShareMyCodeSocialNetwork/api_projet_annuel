package com.esgi.api_project_annuel.web.response;

public class GroupeResponse {
    public int id;
    public String name;

    public GroupeResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public GroupeResponse setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public GroupeResponse setName(String name) {
        this.name = name;
        return this;
    }
}
