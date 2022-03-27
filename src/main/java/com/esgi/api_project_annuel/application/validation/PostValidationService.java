package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Post;

public class PostValidationService {

    public boolean isValid(Post post){
        return !post.getContent().isBlank();
    }
}
