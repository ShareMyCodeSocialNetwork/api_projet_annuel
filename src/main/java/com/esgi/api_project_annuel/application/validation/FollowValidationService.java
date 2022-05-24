package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Follow;

public class FollowValidationService {
    private final UserValidationService userValidationService = new UserValidationService();
    public boolean isValid(Follow follow){
        return userValidationService.isUserValid(follow.getFollowedUser()) && userValidationService.isUserValid(follow.getFollowerUser());
    }
}
