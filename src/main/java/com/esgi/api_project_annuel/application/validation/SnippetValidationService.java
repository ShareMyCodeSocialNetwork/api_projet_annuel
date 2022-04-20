package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Snippet;

public class SnippetValidationService {

    public boolean snippetIsValid(Snippet snippet){return !snippet.getContent().isBlank();}
}
