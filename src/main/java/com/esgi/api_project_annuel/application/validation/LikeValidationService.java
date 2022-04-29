package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Like;

public class LikeValidationService {

    PostValidationService postValidationService =  new PostValidationService();
    UserValidationService userValidationService = new UserValidationService();

    public boolean isValid(Like like){
        return postValidationService.isValid(like.getPost()) && userValidationService.isUserValid(like.getUser());
    }
}
