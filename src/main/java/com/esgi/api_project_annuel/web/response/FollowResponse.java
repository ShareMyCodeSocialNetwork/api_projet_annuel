package com.esgi.api_project_annuel.web.response;

import com.esgi.api_project_annuel.Domain.entities.User;

public class FollowResponse {
    int id;
    public User follower;
    public User followed;

    public FollowResponse() {}

    public User getFollower() {
        return follower;
    }

    public FollowResponse setFollower(User follower) {
        this.follower = follower;
        return this;
    }

    public User getFollowed() {
        return followed;
    }

    public FollowResponse setFollowed(User followed) {
        this.followed = followed;
        return this;
    }

    public int getId() {
        return id;
    }

    public FollowResponse setId(int id) {
        this.id = id;
        return this;
    }
}
