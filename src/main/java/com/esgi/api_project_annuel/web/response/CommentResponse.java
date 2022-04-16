package com.esgi.api_project_annuel.web.response;

public class CommentResponse {
    public int id;
    public String content;
    public int user_id;
    public int post_id;

    public CommentResponse() {}

    public int getId() {
        return id;
    }

    public CommentResponse setId(int id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentResponse setContent(String content) {
        this.content = content;
        return this;
    }

    public int getUser_id() {
        return user_id;
    }

    public CommentResponse setUser_id(int user_id) {
        this.user_id = user_id;
        return this;
    }

    public int getPost_id() {
        return post_id;
    }

    public CommentResponse setPost_id(int post_id) {
        this.post_id = post_id;
        return this;
    }
}
