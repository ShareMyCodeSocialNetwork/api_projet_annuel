package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.Domain.entities.Snippet;
import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.*;
import com.esgi.api_project_annuel.web.controller.fixture.UserFixture;
import com.esgi.api_project_annuel.web.request.*;
import com.esgi.api_project_annuel.web.response.*;
import com.esgi.api_project_annuel.web.response.UserResponse;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserControllerTest {
    @LocalServerPort
    int port;
    GlobalObject globalObject = new GlobalObject();

    @BeforeEach
    void setup(){
        RestAssured.port = port;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    void addUser() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        UserFixture.create(request).then()
                .statusCode(400);

        request.firstname = "";
        request.pseudo = "autre";
        request.email = "autre@autre.com";
        UserFixture.create(request).then()
                .statusCode(400);


        UserFixture.deleteById(response.getId(),TokenFixture.adminToken()).then()
                .statusCode(202);
    }

    @Test
    void getByPseudo() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var token = TokenFixture.getToken(request);

        UserFixture.getByPseudo(request.pseudo,token).then()
                .statusCode(200);
        request.pseudo = "not found";
        UserFixture.getByPseudo(request.pseudo,token).then()
                .statusCode(400);


        UserFixture.deleteById(response.getId(),TokenFixture.adminToken()).then()
                .statusCode(202);
    }

    @Test
    void getByEmail() {
        var request = UserFixture.userToUserRequest(globalObject.buildValidUser());

        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var token = TokenFixture.getToken(request);

        UserFixture.getByEmail(request.email,token).then()
                .statusCode(200);
        request.email = "not found";
        UserFixture.getByEmail(request.email,token).then()
                .statusCode(400);
    }

    @Test
    void getUserAll() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var token = TokenFixture.getToken(request);

        var responseALL  = UserFixture.getAll(token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", UserResponse.class);
        assertThat(responseALL).isNotEmpty();

        UserFixture.deleteById(response.getId(),TokenFixture.adminToken()).then()
                .statusCode(202);
    }

    @Test
    void getUserById() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var token = TokenFixture.getToken(request);

        UserFixture.getById(response.getId(),token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        UserFixture.getById(0,token).then()
                .statusCode(400);


        UserFixture.deleteById(response.getId(),TokenFixture.adminToken()).then()
                .statusCode(202);
    }



    @Test
    void changePassword() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var token = TokenFixture.getToken(request);

        request.password = "NewPassword";
        response = UserFixture.changePassword(response.getId(),request,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);


        request.password = "";
        UserFixture.changePassword(response.getId(),request,token).then()
                .statusCode(400);
    }

    @Test
    void changeEmail() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var token = TokenFixture.getToken(request);

        UserFixture.changeEmail(response.getId(),request, token).then()
        .statusCode(400); //email exist

        request.email = GlobalObject.randomEmail();
        response = UserFixture.changeEmail(response.getId(),request, token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        assertThat(response.getEmail()).isEqualTo(request.email);

        request.email = "";
        UserFixture.changeEmail(response.getId(),request, token).then()
                .statusCode(400);

        UserFixture.deleteById(response.getId(),TokenFixture.adminToken()).then()
                .statusCode(202);
    }

    @Test
    void changePseudo() {
        var request = UserFixture.userToUserRequest(globalObject.buildValidUser());
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var token = TokenFixture.getToken(request);

        request.pseudo = "NewPseudo";
        var response2 = UserFixture.changePseudo(response.getId(),request,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        assertThat(response2.getPseudo()).isEqualTo(request.pseudo);

        UserFixture.changePseudo(response2.getId(),request,token).then()
                .statusCode(400); // pseudo exist

        request.pseudo = "";
        UserFixture.changePseudo(response2.getId(),request,token).then()
                .statusCode(400);

        UserFixture.deleteById(response2.getId(),TokenFixture.adminToken()).then()
                .statusCode(202);
    }

    @Test
    void changeLastname() {
        var request = UserFixture.userToUserRequest(globalObject.buildValidUser());
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var token = TokenFixture.getToken(request);

        request.lastname = "NewName";
        response = UserFixture.changeLastname(response.getId(),request,token ).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        assertThat(response.getLastname()).isEqualTo(request.lastname);

        request.lastname = "";
        UserFixture.changeLastname(response.getId(),request,token).then()
                .statusCode(400);
    }

    @Test
    void changeFirstname() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var token = TokenFixture.getToken(request);

        request.firstname = "NewName";
        response = UserFixture.changeFirstname(response.getId(),request,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        assertThat(response.getFirstname()).isEqualTo(request.firstname);

        request.firstname = "";
        UserFixture.changeFirstname(response.getId(),request,token).then()
                .statusCode(400);

        UserFixture.deleteById(response.getId(),TokenFixture.adminToken()).then()
                .statusCode(202);
    }

    @Test
    void deleteUser() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var token = TokenFixture.login(request).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".",Token.class);

        UserFixture.deleteById(response.getId(),token).then()
                .statusCode(202);

        UserFixture.deleteById(response.getId(),token).then()
                .statusCode(400);
    }

    @Test
    void refreshToken() {
        //todo les param ?
        /*
        // user
        var token  = TokenFixture.userToken();

        TokenFixture.refresh(token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", Token.class);

        // admin
        token = TokenFixture.adminToken();
        TokenFixture.refresh(token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", Token.class);
         */
    }

    @Test
    void loginToken(){
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        //user lambda
        TokenFixture.login(request).then()
                .statusCode(200)
                .extract().body().jsonPath().getJsonObject(".");

        request.password = "mauvais mdp";
        TokenFixture.login(request).then()
                .statusCode(401);

        //admin
        request.email = "lucas@hotmail.fr";
        request.password = "azerty1234";

        var token = TokenFixture.login(request).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".",Token.class);
        System.out.println(token);

    }

    @Test
    void should_delete_all_link_of_user(){
        var token = TokenFixture.userToken();

        /*
          CREATE USER
         */
        var userRequest = UserFixture.userToUserRequest(globalObject.validUser);
        var user = UserFixture.create(userRequest).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        /*
          CREATE FOLLOW
         */
        var followRequest = new FollowRequest();
        followRequest.followerUserId = user.getId();
        followRequest.followedUserId = 1;
        var follow1 = FollowFixture.create(followRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", FollowResponse.class);
        followRequest.followedUserId = user.getId();
        followRequest.followerUserId = 1;
        var follow2 = FollowFixture.create(followRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", FollowResponse.class);


        /*
          CREATE GROUP
         */
        var groupRequest = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        groupRequest.user_id = 3;
        var group = GroupFixture.create(groupRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        /*
          CREATE PROJECT
         */
        var projectRequest = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        projectRequest.group_id = group.id;
        projectRequest.user_id = user.getId();
        var project = ProjectFixture.create(projectRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        /*
          CREATE CODE
         */
        var codeRequest = CodeFixture.codeToCodeRequest(globalObject.validCode);
        codeRequest.userId = user.getId();
        codeRequest.language_id = 1;
        codeRequest.project_id = project.getId();
        var code = CodeFixture.create(codeRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Code.class);

        /*
          CREATE POST
         */
        var request = PostFixture.postToPostRequest(globalObject.validPost);
        request.user_id = user.getId();
        var post = PostFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);
        request.user_id = 3;
        var post2 = PostFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        /*
          CREATE LIKE
         */
        var likeRequest = new LikeRequest();
        likeRequest.post_id = post.getId();
        likeRequest.user_id = user.getId();
        var like = LikeFixture.create(likeRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", LikeResponse.class);
        likeRequest = new LikeRequest();
        likeRequest.post_id = post2.getId();
        likeRequest.user_id = user.getId();
        var like2 = LikeFixture.create(likeRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", LikeResponse.class);


        /*
          CREATE COMMENT
         */
        var commentRequest = CommentFixture.commentToCommentRequest(globalObject.validComment);
        commentRequest.post_id = post.getId();
        commentRequest.user_id = user.getId();
        var comment = CommentFixture.create(commentRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", CommentResponse.class);

        /*
          CREATE SNIPPET
         */
        var snippetRequest = SnippetFixture.snippetToSnippetRequest(globalObject.validSnippet);
        snippetRequest.language_id = 1;
        snippetRequest.user_id = user.getId();

        var snippet = SnippetFixture.create(snippetRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Snippet.class);

        /*
          CREATE USER_ROLE_GROUP
         */
        var userRoleGroupRequest = new UserRoleGroupRequest();
        userRoleGroupRequest.role_id = 1;
        userRoleGroupRequest.user_id = user.getId();
        userRoleGroupRequest.group_id = group.getId();
        var userRoleGroup = UserRoleGroupFixture.create(userRoleGroupRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserRoleGroupResponse.class);



        UserFixture.deleteById(user.getId(), token).then()
                .statusCode(202);


        /*
          check if correctly work
         */
        UserFixture
                .getById(user.getId(), token)
                .then()
                .statusCode(400);
        ProjectFixture
                .getById(project.getId(), token)
                .then()
                .statusCode(404);
        CodeFixture
                .getById(code.getId(), token)
                .then()
                .statusCode(404);
        PostFixture
                .getById(post.getId(), token)
                .then()
                .statusCode(404);
        LikeFixture
                .getById(like.getId(), token)
                .then()
                .statusCode(404);
        LikeFixture
                .getById(like2.getId(), token)
                .then()
                .statusCode(404);
        CommentFixture
                .getById(comment.getId(), token)
                .then()
                .statusCode(404);
        SnippetFixture
                .getById(snippet.getId(), token)
                .then()
                .statusCode(404);
        UserRoleGroupFixture
                .getById(userRoleGroup.getId(), token)
                .then()
                .statusCode(404);
    }

}