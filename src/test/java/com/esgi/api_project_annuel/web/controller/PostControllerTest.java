package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.GroupFixture;
import com.esgi.api_project_annuel.web.controller.fixture.PostFixture;
import com.esgi.api_project_annuel.web.controller.fixture.TokenFixture;
import com.esgi.api_project_annuel.web.response.GroupResponse;
import com.esgi.api_project_annuel.web.response.PostResponse;
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
class PostControllerTest {

    @LocalServerPort
    int port;
    GlobalObject globalObject = new GlobalObject();

    @BeforeEach
    void setup(){
        RestAssured.port = port;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    void addPost() {
        var token = TokenFixture.userToken();
        var request = PostFixture.postToPostRequest(globalObject.validPost);
        request.user_id = 3;
        PostFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);
        request.user_id = 42000;
        PostFixture.create(request,token).then()
                .statusCode(406);
        request.content = "";
        PostFixture.create(request,token).then()
                .statusCode(400);

    }

    @Test
    void getPostById() {
        var token = TokenFixture.userToken();
        var request = PostFixture.postToPostRequest(globalObject.validPost);
        request.user_id = 3;
        var post = PostFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        PostFixture.getById(post.id, token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", PostResponse.class);
        PostFixture.getById(0, token).then()
                .statusCode(404);

    }

    @Test
    void getAll() {
        var token = TokenFixture.userToken();
        var request = PostFixture.postToPostRequest(globalObject.validPost);
        request.user_id = 3;
        PostFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        var posts = PostFixture.getAll(token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", PostResponse.class);

        assertThat(posts).isNotEmpty();
    }

    @Test
    void getAllUserPosts() {
        var token = TokenFixture.userToken();
        var request = PostFixture.postToPostRequest(globalObject.validPost);
        request.user_id = 3;
        PostFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        var posts = PostFixture.getByUser(3,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", PostResponse.class);

        assertThat(posts).isNotEmpty();

        posts = PostFixture.getByUser(1,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", PostResponse.class);

        assertThat(posts).isEmpty();
    }

    @Test
    void changeContent() {
        var token = TokenFixture.userToken();
        var request = PostFixture.postToPostRequest(globalObject.validPost);
        request.user_id = 3;
        var post = PostFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        request.content = "newContent";
        post = PostFixture.changeContent(post.id, request,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", PostResponse.class);
        assertThat(post.content).isEqualTo(request.content);
        request.content = "";
        PostFixture.changeContent(post.id, request,token).then()
                .statusCode(400);
    }

    @Test
    void deletePost() {
        var token = TokenFixture.userToken();
        var request = PostFixture.postToPostRequest(globalObject.validPost);
        request.user_id = 3;
        var post = PostFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);
        PostFixture.deleteById(post.id, token).then()
                .statusCode(204);
        PostFixture.deleteById(post.id, token).then()
                .statusCode(404);

    }
}