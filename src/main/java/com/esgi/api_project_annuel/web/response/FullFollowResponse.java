package com.esgi.api_project_annuel.web.response;

import com.esgi.api_project_annuel.Domain.entities.Follow;

import java.util.List;

public class FullFollowResponse {
    private List<Follow> followed;
    private List<Follow> followers;
    private Follow isFollow;

    public List<Follow> getFollowed() {
        return followed;
    }

    public FullFollowResponse setFollowed(List<Follow> followed) {
        this.followed = followed;
        return this;
    }

    public List<Follow> getFollowers() {
        return followers;
    }

    public FullFollowResponse setFollowers(List<Follow> followers) {
        this.followers = followers;
        return this;
    }

    public Follow getIsFollow() {
        return isFollow;
    }

    public FullFollowResponse setIsFollow(Follow isFollow) {
        this.isFollow = isFollow;
        return this;
    }
}
