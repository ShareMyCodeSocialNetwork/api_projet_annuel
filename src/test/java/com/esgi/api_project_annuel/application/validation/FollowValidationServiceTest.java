package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.GlobalObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FollowValidationServiceTest {
    GlobalObject globalObject = new GlobalObject();
    FollowValidationService validationService = new FollowValidationService();
    @Test
    void isValid() {
        assertThat(validationService.isValid(globalObject.validFollow)).isEqualTo(true);
        var invalid = globalObject.validFollow;
        invalid.setFollowedUser(null);
        invalid.setFollowerUser(null);
        assertThat(validationService.isValid(invalid)).isEqualTo(false);
    }
}