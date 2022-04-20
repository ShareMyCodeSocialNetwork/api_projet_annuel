package com.esgi.api_project_annuel.web.response;

import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;

public class CommentResponse {
    public int id;
    public String content;
    public User user;
    public Post post;

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

    public User getUser() {
        return user;
    }

    public CommentResponse setUser(User user) {
        this.user = user;
        return this;
    }

    public Post getPost() {
        return post;
    }

    public CommentResponse setPost(Post post) {
        this.post = post;
        return this;
    }
}
