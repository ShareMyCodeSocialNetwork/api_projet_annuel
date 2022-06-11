package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.GlobalObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LanguageValidationServiceTest {

    GlobalObject globalObject = new GlobalObject();
    LanguageValidationService validationService = new LanguageValidationService();
    @Test
    void languageIsValid() {
        assertThat(validationService.languageIsValid(globalObject.validLanguage)).isEqualTo(true);
        var invalid = globalObject.validLanguage;
        invalid.setName("");
        assertThat(validationService.languageIsValid(invalid)).isEqualTo(false);
        assertThat(validationService.languageIsValid(null)).isEqualTo(false);
    }
}