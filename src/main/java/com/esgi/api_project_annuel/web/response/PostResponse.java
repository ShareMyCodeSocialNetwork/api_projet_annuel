package com.esgi.api_project_annuel.web.response;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.Domain.entities.User;

public class PostResponse {
    public int id;
    public String content;
    public User user;
    public Code code;

    public PostResponse() {}


    public int getId() {
        return id;
    }

    public PostResponse setId(int id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public PostResponse setContent(String content) {
        this.content = content;
        return this;
    }

    public User getUser() {
        return user;
    }

    public PostResponse setUser(User user) {
        this.user = user;
        return this;
    }

    public Code getCode() {
        return code;
    }

    public PostResponse setCode(Code code) {
        this.code = code;
        return this;
    }
}
