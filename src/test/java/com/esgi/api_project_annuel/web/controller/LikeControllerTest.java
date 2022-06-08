package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.LikeFixture;
import com.esgi.api_project_annuel.web.controller.fixture.PostFixture;
import com.esgi.api_project_annuel.web.controller.fixture.TokenFixture;
import com.esgi.api_project_annuel.web.request.LikeRequest;
import com.esgi.api_project_annuel.web.response.LikeResponse;
import com.esgi.api_project_annuel.web.response.PostResponse;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class LikeControllerTest {
    @LocalServerPort
    int port;
    GlobalObject globalObject = new GlobalObject();

    @BeforeEach
    void setup(){
        RestAssured.port = port;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    void addLike() {
        var token = TokenFixture.userToken();

        var postRequest = PostFixture.postToPostRequest(globalObject.validPost);
        postRequest.user_id = 3;
        var post = PostFixture.create(postRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        var request = new LikeRequest();
        request.post_id = post.getId();
        request.user_id = 3;

        var like = LikeFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", LikeResponse.class);
        assertThat(like.getPost().getId()).isEqualTo(request.post_id);
        assertThat(like.getUser().getId()).isEqualTo(request.user_id);

        LikeFixture.create(request,token).then()
                .statusCode(400);
    }

    @Test
    void getAll() {
        var token = TokenFixture.userToken();

        var postRequest = PostFixture.postToPostRequest(globalObject.validPost);
        postRequest.user_id = 3;
        var post = PostFixture.create(postRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        var request = new LikeRequest();
        request.post_id = post.getId();
        request.user_id = 3;

        LikeFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", LikeResponse.class);

        var likes = LikeFixture.getAll(token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", LikeResponse.class);
        assertThat(likes).isNotEmpty();
    }

    @Test
    void getLikeById() {
        var token = TokenFixture.userToken();

        var postRequest = PostFixture.postToPostRequest(globalObject.validPost);
        postRequest.user_id = 3;
        var post = PostFixture.create(postRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        var request = new LikeRequest();
        request.post_id = post.getId();
        request.user_id = 3;

        var like = LikeFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", LikeResponse.class);

        like = LikeFixture.getById(like.getId(), token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", LikeResponse.class);

        assertThat(like.getPost().getId()).isEqualTo(request.post_id);
        assertThat(like.getUser().getId()).isEqualTo(request.user_id);

        LikeFixture.getById(0, token).then()
                .statusCode(404);
    }

    @Test
    void getLikeByPost() {
        var token = TokenFixture.userToken();

        var postRequest = PostFixture.postToPostRequest(globalObject.validPost);
        postRequest.user_id = 3;
        var post = PostFixture.create(postRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        var request = new LikeRequest();
        request.post_id = post.getId();
        request.user_id = 3;

        LikeFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", LikeResponse.class);

        var likes = LikeFixture.getByPost( post.getId(), token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", LikeResponse.class);
        assertThat(likes).isNotEmpty();
    }

    @Test
    void deleteLike() {
        var token = TokenFixture.userToken();

        var postRequest = PostFixture.postToPostRequest(globalObject.validPost);
        postRequest.user_id = 3;
        var post = PostFixture.create(postRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        var request = new LikeRequest();
        request.post_id = post.getId();
        request.user_id = 3;

        var like = LikeFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", LikeResponse.class);

        LikeFixture.deleteById(like.getId(), token).then()
                .statusCode(204);
        LikeFixture.deleteById(like.getId(), token).then()
                .statusCode(404);
    }
}