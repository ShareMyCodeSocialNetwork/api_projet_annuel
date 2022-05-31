package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.GlobalObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LikeValidationServiceTest {

    GlobalObject globalObject = new GlobalObject();
    LikeValidationService validationService = new LikeValidationService();
    @Test
    void isValid() {
        assertThat(validationService.isValid(globalObject.validLike)).isEqualTo(true);
        var invalid = globalObject.validLike;
        invalid.setUser(null);
        invalid.setPost(null);
        assertThat(validationService.isValid(invalid)).isEqualTo(false);
        assertThat(validationService.isValid(null)).isEqualTo(false);
    }
}