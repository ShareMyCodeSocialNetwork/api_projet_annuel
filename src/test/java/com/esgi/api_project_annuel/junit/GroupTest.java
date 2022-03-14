package com.esgi.api_project_annuel.junit;

import com.esgi.api_project_annuel.model.Group;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GroupTest {
    private Group validGroup;
    private Group invalidGroup;

    @Before
    public void init() {
        String validName = "validName";
        String invalidName = "";

        validGroup = new Group(validName);
        invalidGroup = new Group(invalidName);
    }

    @Test
    public void isValidGroup() {
        assertTrue(validGroup.isValid());
    }

    @Test
    public void isInvalidGroup() {
        assertFalse(invalidGroup.isValid());
    }

}
