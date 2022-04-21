package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Code;

public class CodeValidationService {

    public boolean codeIsValid(Code code){
        return !code.getContent().isBlank();
    }

}
