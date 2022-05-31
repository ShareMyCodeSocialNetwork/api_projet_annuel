package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.GlobalObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostValidationServiceTest {

    GlobalObject globalObject = new GlobalObject();
    PostValidationService validationService = new PostValidationService();


    @Test
    void isValid() {
        assertThat(validationService.isValid(globalObject.validPost)).isEqualTo(true);
        var invalid = globalObject.validPost;
        invalid.setContent("");
        assertThat(validationService.isValid(invalid)).isEqualTo(false);
        assertThat(validationService.isValid(null)).isEqualTo(false);
    }
}