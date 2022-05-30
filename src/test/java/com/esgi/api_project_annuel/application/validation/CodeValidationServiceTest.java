package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.Domain.entities.Project;
import com.esgi.api_project_annuel.Domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CodeValidationServiceTest {

    Code valid = new Code();
    Code invalid = new Code();
    Project project = new Project();
    CodeValidationService validationService = new CodeValidationService();
    @BeforeEach
    void setUp() {
        Project project = new Project();
        User user = new User();


        project.setOwner(user);
        valid.setNameCode("code");
        valid.setProject(project);
    }

    @Test
    void codeIsValid() {
       // assertThat(validationService.codeIsValid(valid)).isEqualTo(true);
        assertThat(validationService.codeIsValid(invalid)).isEqualTo(false);
    }
}