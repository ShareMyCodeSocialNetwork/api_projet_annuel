package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Language;

public class LanguageValidationService {

    public boolean languageIsValid(Language language){return !language.getName().isBlank();}

}
