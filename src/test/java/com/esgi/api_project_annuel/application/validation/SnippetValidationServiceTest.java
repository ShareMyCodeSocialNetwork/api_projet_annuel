package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.GlobalObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;

class SnippetValidationServiceTest {

    GlobalObject globalObject = new GlobalObject();
    SnippetValidationService validationService = new SnippetValidationService();
    @Test
    void snippetIsValid() {
        assertThat(validationService.snippetIsValid(globalObject.validSnippet)).isEqualTo(true);
        var invalid = globalObject.validSnippet;
        invalid.setUser(null);
        invalid.setName("");
        invalid.setLanguage(null);
        invalid.setContent("");
        assertThat(validationService.snippetIsValid(invalid)).isEqualTo(false);
        assertThat(validationService.snippetIsValid(null)).isEqualTo(false);
    }
}