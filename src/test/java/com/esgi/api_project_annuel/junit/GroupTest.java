package com.esgi.api_project_annuel.junit;

import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.application.validation.GroupValidationService;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GroupTest {
    private Group validGroup;
    private Group invalidGroup;
    private final GroupValidationService groupValidationService = new GroupValidationService();

    @Before
    public void init() {
        String validName = "validName";
        String invalidName = "";


        validGroup = new Group();
      invalidGroup = new Group();
    }

//    @Test
//    public void isValidGroup() {
//        assertTrue(groupValidationService.isValid(validGroup));
//    }
//
//    @Test
//    public void isInvalidGroup() {
//        assertFalse(groupValidationService.isValid(invalidGroup));
//    }

}
