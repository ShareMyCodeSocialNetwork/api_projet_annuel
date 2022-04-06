package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Comment;

public class CommentValidationService {

    public boolean isValid(Comment comment){
        return !comment.getContent().isBlank();
    }
}
