package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.CommentFixture;
import com.esgi.api_project_annuel.web.controller.fixture.PostFixture;
import com.esgi.api_project_annuel.web.controller.fixture.TokenFixture;
import com.esgi.api_project_annuel.web.response.CommentResponse;
import com.esgi.api_project_annuel.web.response.PostResponse;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CommentControllerTest {
    @LocalServerPort
    int port;
    GlobalObject globalObject = new GlobalObject();

    @BeforeEach
    void setup(){
        RestAssured.port = port;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    void createCommentOnPost() {
        var token = TokenFixture.userToken();

        var postRequest = PostFixture.postToPostRequest(globalObject.validPost);
        postRequest.user_id = 3;
        var post = PostFixture.create(postRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        var request = CommentFixture.commentToCommentRequest(globalObject.validComment);
        request.post_id = post.getId();
        request.user_id = 3;
        CommentFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", CommentResponse.class);

        request.user_id = 42000;
        CommentFixture.create(request,token).then()
                .statusCode(406);

        request.content = "";
        request.user_id = 0;
        request.post_id = 0;
        CommentFixture.create(request,token).then()
                .statusCode(400);

    }

    @Test
    void getByUser() {
        var token = TokenFixture.userToken();

        var postRequest = PostFixture.postToPostRequest(globalObject.validPost);
        postRequest.user_id = 3;
        var post = PostFixture.create(postRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        var request = CommentFixture.commentToCommentRequest(globalObject.validComment);
        request.post_id = post.getId();
        request.user_id = 3;
        CommentFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", CommentResponse.class);

    }

    @Test
    void getByPost() {
    }

    @Test
    void changeContent() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }



    @Test
    void deleteComment() {
    }
}