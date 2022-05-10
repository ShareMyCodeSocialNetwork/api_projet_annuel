package com.esgi.api_project_annuel.web.response;

import com.esgi.api_project_annuel.Domain.entities.User;

public class CollectionResponse {
    public int id;
    public String name;
    public User user;

    public CollectionResponse() {
    }

    public int getId() {
        return id;
    }

    public CollectionResponse setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CollectionResponse setName(String name) {
        this.name = name;
        return this;
    }

    public User getUser() {
        return user;
    }

    public CollectionResponse setUser(User user) {
        this.user = user;
        return this;
    }
}
