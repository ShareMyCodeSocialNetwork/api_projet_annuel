package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Snippet;

public class SnippetValidationService {

    private final LanguageValidationService languageValidationService = new LanguageValidationService();
    private final UserValidationService userValidationService = new UserValidationService();

    public boolean snippetIsValid(Snippet snippet){
        if(snippet  == null) return false;
        return languageValidationService.languageIsValid(snippet.getLanguage()) &&
                userValidationService.isUserValid(snippet.getUser()) &&
                !snippet.getContent().equals("") &&
                !snippet.getContent().isEmpty() &&
                !snippet.getName().equals("") &&
                !snippet.getName().isEmpty();
    }
}
