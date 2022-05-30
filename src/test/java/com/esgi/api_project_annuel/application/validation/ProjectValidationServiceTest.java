package com.esgi.api_project_annuel.application.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProjectValidationServiceTest {

    GlobalObject globalObject = new GlobalObject();
    ProjectValidationService validationService = new ProjectValidationService();
    @Test
    void isValid() {
        assertThat(validationService.isValid(globalObject.validProject)).isEqualTo(true);
        assertThat(validationService.isValid(null)).isEqualTo(false);
    }
}