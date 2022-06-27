package com.esgi.api_project_annuel;

import com.esgi.api_project_annuel.Domain.entities.*;
import org.checkerframework.checker.units.qual.C;

import java.util.UUID;

public class GlobalObject {
    public User validUser;
    public Code validCode;
    public Language validLanguage;
    public Project validProject;
    public Group validGroup;
    public Comment validComment;
    public Post validPost;
    public Role validRole;
    public Follow validFollow;
    public Like validLike;
    public Snippet validSnippet;
    public UserRoleGroup validUserRoleGroup;
    public User adminUser;
    public User lambdaUser;

    public GlobalObject() {
        validUser = buildValidUser();
        validLanguage = buildValidLanguage();
        validProject = buildValidProject();
        validGroup = buildValidGroup();
        validCode = buildValidCode();
        validComment = buildValidComment();
        validPost = buildValidPost();
        validRole = buildValidRole();
        validFollow = buildValidFollow();
        validLike = buildValidLike();
        validSnippet = buildValidSnippet();
        validUserRoleGroup = buildValidUserRoleGroup();
    }

    public UserRoleGroup buildValidUserRoleGroup() {
        var userRoleGroup = new UserRoleGroup();
        userRoleGroup.setUser(validUser);
        userRoleGroup.setGroup(validGroup);
        userRoleGroup.setRole(validRole);
        return userRoleGroup;
    }

    public Snippet buildValidSnippet() {
        var snippet = new Snippet();
        snippet.setContent("valid content");
        snippet.setLanguage(validLanguage);
        snippet.setName("valid name");
        snippet.setUser(validUser);
        return snippet;
    }

    public Like buildValidLike() {
        var like = new Like();
        like.setPost(validPost);
        like.setUser(validUser);
        return like;
    }

    public Follow buildValidFollow() {
        var follow = new Follow();
        follow.setFollowerUser(validUser);
        follow.setFollowedUser(validUser);
        return follow;
    }

    public Role buildValidRole() {
        var role = new Role();
        role.setTitlePermission(randomPseudo());
        return role;
    }

    public Post buildValidPost() {
        var post = new Post();
        post.setContent("validContent");
        post.setUser(validUser);
        return post;
    }


    public User buildValidUser(){
        var  user = new User();
        user.setEmail(randomEmail());
        user.setFirstname("Lastname");
        user.setLastname("Firstname");
        user.setPassword("PasswordValid");
        user.setPseudo(randomPseudo());
        user.setProfilePicture("profilePicture");
        return user;
    }

    public Comment buildValidComment(){
        var comment = new Comment();
        comment.setContent("valid content");
        comment.setUser(validUser);
        comment.setPost(validPost);
        return comment;
    }
    public Code buildValidCode(){
        var code = new Code();
        code.setNameCode("validName");
        code.setUser(validUser);
        code.setContent("validContent");
        code.setLanguage(validLanguage);
        code.setProject(validProject);
        return code;
    }

    public Project buildValidProject() {
        var project = new Project();
        project.setOwner(validUser);
        project.setGroup(validGroup);
        project.setName("valid name");
        project.setDescription("valid description");
        return project;
    }

    public Language buildValidLanguage() {
        var language = new Language();
        language.setName("valid name");
        return language;
    }
    public Group buildValidGroup() {
        var group = new Group();
        group.setName("valid name");
        group.setDescription("valid description");
        return group;
    }

    public static String randomEmail() {
        return "random-" + UUID.randomUUID() + "@example.com";
    }
    public static String randomPseudo(){
        return "random-pseudo" + UUID.randomUUID();
    }
}
