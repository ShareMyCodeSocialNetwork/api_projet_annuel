package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.GlobalObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserValidationServiceTest {
    //TESTS FAIT PAR DAVID HENRI, fichier recreer car un rename ne comptabilisait pas dans le code coverage
    private User invalidUser;
    private User invalidUserWithShortPassword;
    private User invalidUserWithLongPassword;
    private User invalidUserWithBadMail;
    private User invalidUserWithBadFirstname;
    private User invalidUserWithBadLastname;
    private final UserValidationService userValidationService = new UserValidationService();

    GlobalObject globalObject = new GlobalObject();

    public User UserObject(String lastName, String firstName,String password,String email,String pseudo){
        User userObject = new User();
        userObject.setEmail(email);
        userObject.setFirstname(firstName);
        userObject.setLastname(lastName);
        userObject.setPassword(password);
        userObject.setPseudo(pseudo);
        return userObject;
    }
    @BeforeEach
    void setUp() {
        String validMail = "validMail@gmail.com";
        String invalidMail = "invalidMail@.com";
        String validPassword = "PasswordValid";
        String invalidTooLongPassword = "invalid";
        String invalidTooSmallPassword = "TooBigPasswordToBeAValidUser123456789";
        String validFirstname = "Firstname";
        String invalidFirstname = "";
        String validLastname = "Lastname";
        String invalidLastname = "";
        String validPseudo = "pseudo";
        String invalidPseudo = "pseudo";


        invalidUser = UserObject(invalidFirstname, invalidLastname, invalidTooSmallPassword,invalidMail,invalidPseudo);
        invalidUserWithLongPassword = UserObject( validFirstname, validLastname, invalidTooLongPassword,validMail,validPseudo);
        invalidUserWithShortPassword = UserObject( validFirstname, validLastname, invalidTooSmallPassword,validMail,validPseudo);
        invalidUserWithBadMail = UserObject(validFirstname, validLastname, validPassword,invalidMail,validPseudo);
        invalidUserWithBadFirstname = UserObject( invalidFirstname, validLastname, validPassword,validMail,validPseudo);
        invalidUserWithBadLastname = UserObject( validFirstname, invalidLastname, validPassword,validMail,validPseudo);
    }

    @Test
    void isUserValid() {
        Assertions.assertFalse(userValidationService.isUserValid(invalidUserWithBadFirstname));
        Assertions.assertTrue(userValidationService.isUserValid(globalObject.validUser));
        Assertions.assertFalse(userValidationService.isUserValid(invalidUserWithLongPassword));
        Assertions.assertFalse(userValidationService.isUserValid(invalidUserWithBadLastname));
        Assertions.assertFalse(userValidationService.isUserValid(invalidUser));
        Assertions.assertFalse(userValidationService.isUserValid(invalidUserWithShortPassword));
        Assertions.assertFalse(userValidationService.isUserValid(invalidUserWithBadMail));
        Assertions.assertFalse(userValidationService.isUserValid(null));
    }
}