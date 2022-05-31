package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.GlobalObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GroupValidationServiceTest {

    GlobalObject globalObject = new GlobalObject();
    GroupValidationService validationService = new GroupValidationService();
    @Test
    void isValid() {
        assertThat(validationService.isValid(globalObject.validGroup)).isEqualTo(true);
        var invalidGroup = globalObject.validGroup;
        invalidGroup.setName("");
        assertThat(validationService.isValid(invalidGroup)).isEqualTo(false);
        assertThat(validationService.isValid(null)).isEqualTo(false);
    }
}