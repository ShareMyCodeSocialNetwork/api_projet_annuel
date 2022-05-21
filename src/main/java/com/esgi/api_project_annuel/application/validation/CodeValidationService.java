package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Code;

public class CodeValidationService {

    private final UserValidationService userValidationService = new UserValidationService();
    private final LanguageValidationService languageValidationService = new LanguageValidationService();
    public boolean codeIsValid(Code code){
        return languageValidationService.languageIsValid(code.getLanguage()) &&
                userValidationService.isUserValid(code.getUser()) &&
                !code.getContent().equals("") &&
                !code.getContent().isEmpty() &&
                !code.getNameCode().equals("") &&
                !code.getNameCode().isEmpty();
    }

}
