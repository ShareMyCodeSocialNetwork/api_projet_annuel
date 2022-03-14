package com.esgi.api_project_annuel.junit;
<<<<<<< HEAD
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.application.validation.UserValidationService;
=======
import com.esgi.api_project_annuel.model.User;
>>>>>>> master
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {
    private User validUser;
    private User invalidUser;
    private User invalidUserWithShortPassword;
    private User invalidUserWithLongPassword;
    private User invalidUserWithBadMail;
    private User invalidUserWithBadFirstname;
    private User invalidUserWithBadLastname;
    private  UserValidationService userValidationService = new UserValidationService();

    public User UserObject(String lastName, String firstName,String password,String email){
        User userObject = new User();
        userObject.setEmail(email);
        userObject.setFirstName(firstName);
        userObject.setLastName(lastName);
        userObject.setPassword(password);
        return userObject;
    }


    @Before
    public void init() {
        String validMail = "validMail@gmail.com";
        String invalidMail = "invalidMail@.com";
        String validPassword = "PasswordValid";
        String invalidTooLongPassword = "invalid";
        String invalidTooSmallPassword = "TooBigPasswordToBeAValidUser123456789";
        String validFirstname = "Firstname";
        String invalidFirstname = "";
        String validLastname = "Lastname";
        String invalidLastname = "";


        validUser =  UserObject(validFirstname, validLastname, validPassword,validMail);
        invalidUser = UserObject(invalidFirstname, invalidLastname, invalidTooSmallPassword,invalidMail);
        invalidUserWithLongPassword = UserObject( validFirstname, validLastname, invalidTooLongPassword,validMail);
        invalidUserWithShortPassword = UserObject( validFirstname, validLastname, invalidTooSmallPassword,validMail);
        invalidUserWithBadMail = UserObject(validFirstname, validLastname, validPassword,invalidMail);
        invalidUserWithBadFirstname = UserObject( invalidFirstname, validLastname, validPassword,validMail);
        invalidUserWithBadLastname = UserObject( validFirstname, invalidLastname, validPassword,validMail);
    }

    @Test
    public void isValidUser() {
        assertTrue(userValidationService.isUserValid(validUser));
    }

    @Test
    public void isInvalidUser() {
        assertFalse(userValidationService.isUserValid(invalidUser));
    }

    @Test
    public void isInvalidMail() {
        assertFalse(userValidationService.isUserValid(invalidUserWithBadMail));
    }

    @Test
    public void isTooShortPassword() {
        assertFalse(userValidationService.isUserValid(invalidUserWithShortPassword));
    }

    @Test
    public void isTooLongPassword() {
        assertFalse(userValidationService.isUserValid(invalidUserWithLongPassword));
    }

    @Test
    public void isInvalidFirstname() {
        assertFalse(userValidationService.isUserValid(invalidUserWithBadFirstname));
    }

    @Test
    public void isInvalidLastname() {
        assertFalse(userValidationService.isUserValid(invalidUserWithBadLastname));
    }

}