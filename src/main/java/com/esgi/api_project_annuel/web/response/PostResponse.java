package com.esgi.api_project_annuel.web.response;

public class PostResponse {
    public int id;
    public String content;
    public int user_id;

    //public PostResponse() {}


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

    public int getUser_id() {
        return user_id;
    }

    public PostResponse setUser_id(int user_id) {
        this.user_id = user_id;
        return this;
    }
}
