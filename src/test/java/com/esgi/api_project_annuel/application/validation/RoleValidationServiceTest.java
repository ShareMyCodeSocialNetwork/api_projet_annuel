package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.GlobalObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RoleValidationServiceTest {

    GlobalObject globalObject = new GlobalObject();
    RoleValidationService validationService = new RoleValidationService();
    @Test
    void isValid() {
        assertThat(validationService.isValid(globalObject.validRole)).isEqualTo(true);
        var invalid = globalObject.validRole;
        invalid.setTitlePermission("");
        assertThat(validationService.isValid(invalid)).isEqualTo(false);
        assertThat(validationService.isValid(null)).isEqualTo(false);
    }
}