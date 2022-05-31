package com.esgi.api_project_annuel.application.validation;

import com.esgi.api_project_annuel.Domain.entities.*;
import org.checkerframework.checker.units.qual.C;

public class GlobalObject {
    public User validUser;
    public Code validCode;
    public Language validLanguage;
    public Project validProject;
    public Group validGroup;
    public Comment validComment;

    public GlobalObject() {
        validUser = buildValidUser();
        validLanguage = buildValidLanguage();
        validProject = buildValidProject();
        validGroup = buildValidGroup();
        validCode = buildValidCode();
        validComment = new Comment();
    }

    private User buildValidUser(){
        var  user = new User();
        user.setEmail("validMail@gmail.com");
        user.setFirstname("Lastname");
        user.setLastname("Firstname");
        user.setPassword("PasswordValid");
        user.setPseudo("pseudo");
        user.setProfilePicture("profilePicture");
        return user;
    }
    private Code buildValidCode(){
        var code = new Code();
        code.setNameCode("validName");
        code.setUser(validUser);
        code.setContent("validContent");
        code.setLanguage(validLanguage);
        code.setProject(validProject);
        return code;
    }

    private Project buildValidProject() {
        var project = new Project();
        project.setOwner(validUser);
        project.setGroup(validGroup);
        project.setName("valid name");
        return project;
    }

    private Language buildValidLanguage() {
        var language = new Language();
        language.setName("valid name");
        return language;
    }
    private Group buildValidGroup() {
        var group = new Group();
        group.setName("valid name");
        return group;
    }
}
