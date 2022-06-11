package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.GlobalObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectValidationServiceTest {

    GlobalObject globalObject = new GlobalObject();
    ProjectValidationService validationService = new ProjectValidationService();
    @Test
    void isValid() {
        assertThat(validationService.isValid(globalObject.validProject)).isEqualTo(true);
        var invalid = globalObject.validProject;
        invalid.setName("");
        assertThat(validationService.isValid(invalid)).isEqualTo(false);
        assertThat(validationService.isValid(null)).isEqualTo(false);
    }
}