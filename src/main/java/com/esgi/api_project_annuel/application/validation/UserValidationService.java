package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.User;

public class UserValidationService {


    public UserValidationService(){
    }

    public boolean isUserValid(User user) {
        if(user == null)
            return false;
        return  !user.getFirstname().isBlank() && !user.getLastname().isBlank() && new EmailValidation().isValid(user.getEmail()) && user.getPassword().length() >= 8 && user.getPassword().length() <= 30;

    }

}
