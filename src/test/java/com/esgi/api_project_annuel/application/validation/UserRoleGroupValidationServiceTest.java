package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.GlobalObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserRoleGroupValidationServiceTest {
    GlobalObject globalObject = new GlobalObject();
    UserRoleGroupValidationService validationService = new UserRoleGroupValidationService();
    @Test
    void isValid() {
        assertThat(validationService.isValid(globalObject.validUserRoleGroup)).isEqualTo(true);
        var invalid = globalObject.validUserRoleGroup;
        invalid.setUser(null);
        invalid.setRole(null);
        invalid.setGroup(null);
        assertThat(validationService.isValid(invalid)).isEqualTo(false);
        assertThat(validationService.isValid(null)).isEqualTo(false);
    }
}