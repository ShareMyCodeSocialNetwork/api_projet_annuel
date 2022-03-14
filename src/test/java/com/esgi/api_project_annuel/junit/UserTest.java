package com.esgi.api_project_annuel.junit;
import com.esgi.api_project_annuel.integration.User;
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

        validUser = new User(validMail, validFirstname, validLastname, validPassword);
        invalidUser = new User(invalidMail, invalidFirstname, invalidLastname, invalidTooSmallPassword);
        invalidUserWithLongPassword = new User(validMail, validFirstname, validLastname, invalidTooLongPassword);
        invalidUserWithShortPassword = new User(validMail, validFirstname, validLastname, invalidTooSmallPassword);
        invalidUserWithBadMail = new User(invalidMail, validFirstname, validLastname, validPassword);
        invalidUserWithBadFirstname = new User(validMail, invalidFirstname, validLastname, validPassword);
        invalidUserWithBadLastname = new User(validMail, validFirstname, invalidLastname, validPassword);
    }

    @Test
    public void isValidUser() {
        assertTrue(validUser.isValid());
    }

    @Test
    public void isInvalidUser() {
        assertFalse(invalidUser.isValid());
    }

    @Test
    public void isInvalidMail() {
        assertFalse(invalidUserWithBadMail.isValid());
    }

    @Test
    public void isTooShortPassword() {
        assertFalse(invalidUserWithShortPassword.isValid());
    }

    @Test
    public void isTooLongPassword() {assertFalse(invalidUserWithLongPassword.isValid());}

    @Test
    public void isInvalidFirstname() {assertFalse(invalidUserWithBadFirstname.isValid());}

    @Test
    public void isInvalidLastname() {assertFalse(invalidUserWithBadLastname.isValid());}

}