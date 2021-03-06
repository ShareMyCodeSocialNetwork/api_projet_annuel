package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.GlobalObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CodeValidationServiceTest {

    GlobalObject globalObject = new GlobalObject();
    CodeValidationService validationService = new CodeValidationService();
    @BeforeEach
    void setUp() {

    }

    @Test
    void codeIsValid() {
        assertThat(validationService.codeIsValid(globalObject.validCode)).isEqualTo(true);
        assertThat(validationService.codeIsValid(new Code())).isEqualTo(false);
        assertThat(validationService.codeIsValid(null)).isEqualTo(false);
    }
}