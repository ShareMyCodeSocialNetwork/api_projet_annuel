package com.esgi.api_project_annuel.web.response;

import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;

public class LikeResponse {
    public int id;
    public User user;
    public Post post;

    public LikeResponse() {}

    public int getId() {
        return id;
    }

    public LikeResponse setId(int id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public LikeResponse setUser(User user) {
        this.user = user;
        return this;
    }

    public Post getPost() {
        return post;
    }

    public LikeResponse setPost(Post post) {
        this.post = post;
        return this;
     }
}
