package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.Domain.entities.Comment;
import com.esgi.api_project_annuel.GlobalObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommentValidationServiceTest {

    GlobalObject globalObject = new GlobalObject();
    CommentValidationService validationService = new CommentValidationService();
    @Test
    void isValid() {
        assertThat(validationService.isValid(globalObject.validComment)).isEqualTo(true);
        var invalid = globalObject.validComment;
        invalid.setContent("");
        assertThat(validationService.isValid(invalid)).isEqualTo(false);
        assertThat(validationService.isValid(null)).isEqualTo(false);
    }
}