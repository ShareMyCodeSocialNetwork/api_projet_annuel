package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.kernel.logger.Logger;
import com.esgi.api_project_annuel.kernel.logger.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

public class UserValidationService {


    public UserValidationService(){
    }

    public boolean isUserValid(User user) {

        return  !user.getFirstName().isBlank() && !user.getLastName().isBlank() && new EmailValidation().isValid(user.getEmail()) && user.getPassword().length() >= 8 && user.getPassword().length() <= 30;
        //todo : voir pour renvoyer la bonne erreur
    }

}
