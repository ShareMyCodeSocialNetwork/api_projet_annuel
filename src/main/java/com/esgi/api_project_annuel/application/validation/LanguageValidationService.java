package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Language;

public class LanguageValidationService {

    public boolean languageIsValid(Language language){
        if(language == null)
            return false;
        return !language.getName().isBlank() && !language.getName().equals("");
    }

}
