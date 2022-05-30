package com.esgi.api_project_annuel.application.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
class EmailValidationTest {
    EmailValidation emailValidation= new EmailValidation();

    @Test
    void isValid() {
        var email = "lucas.jehanno@gmail.com";
        assertThat(emailValidation.isValid(email)).isEqualTo(true);
        email = "lucas.jehanno@";
        assertThat(emailValidation.isValid(email)).isEqualTo(false);
    }
}