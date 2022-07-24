package com.esgi.api_project_annuel.web.response;

import com.esgi.api_project_annuel.Domain.entities.Comment;
import com.esgi.api_project_annuel.Domain.entities.Like;
import com.esgi.api_project_annuel.Domain.entities.Post;

import java.util.List;

public class FullPostResponse {
    private Post post;
    private List<Comment> comments;
    private List<Like> likes;

    public Post getPost() {
        return post;
    }

    public FullPostResponse setPost(Post post) {
        this.post = post;
        return this;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public FullPostResponse setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public FullPostResponse setLikes(List<Like> likes) {
        this.likes = likes;
        return this;
    }
}
