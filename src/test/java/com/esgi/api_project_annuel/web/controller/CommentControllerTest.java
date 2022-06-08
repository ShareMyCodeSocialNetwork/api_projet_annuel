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

import static org.assertj.core.api.Assertions.assertThat;
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

        var comments = CommentFixture.getByUser(3,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".",CommentResponse.class);
        assertThat(comments).isNotEmpty();

        comments = CommentFixture.getByUser(0,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".",CommentResponse.class);
        assertThat(comments).isEmpty();
    }

    @Test
    void getByPost() {
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

        var comments = CommentFixture.getByPost(request.post_id, token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".",CommentResponse.class);
        assertThat(comments).isNotEmpty();

        comments = CommentFixture.getByPost(0,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".",CommentResponse.class);
        assertThat(comments).isEmpty();
    }

    @Test
    void changeContent() {
        var token = TokenFixture.userToken();

        var postRequest = PostFixture.postToPostRequest(globalObject.validPost);
        postRequest.user_id = 3;
        var post = PostFixture.create(postRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);


        var request = CommentFixture.commentToCommentRequest(globalObject.validComment);
        request.post_id = post.getId();
        request.user_id = 3;
        var created = CommentFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", CommentResponse.class);

        request.content = "new";
        var updated = CommentFixture.update(created.getId(), request, token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", CommentResponse.class);
        assertThat(updated.getContent()).isEqualTo(request.content);
        assertThat(updated.getId()).isEqualTo(created.getId());

        request.content = "";
        CommentFixture.update(created.getId(), request, token).then()
                .statusCode(400);

        CommentFixture.update(0, request, token).then()
                .statusCode(404);
    }

    @Test
    void getAll() {
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

        var comments = CommentFixture.getAll(token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".",CommentResponse.class);
        assertThat(comments).isNotEmpty();

    }

    @Test
    void getById() {
        var token = TokenFixture.userToken();

        var postRequest = PostFixture.postToPostRequest(globalObject.validPost);
        postRequest.user_id = 3;
        var post = PostFixture.create(postRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        var request = CommentFixture.commentToCommentRequest(globalObject.validComment);
        request.post_id = post.getId();
        request.user_id = 3;
        var created = CommentFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", CommentResponse.class);

        var comments = CommentFixture.getById(created.getId(), token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".",CommentResponse.class);

        assertThat(comments.getId()).isEqualTo(created.getId());
        assertThat(comments.getContent()).isEqualTo(created.getContent());
        assertThat(comments.getPost().getId()).isEqualTo(created.getPost().getId());
        assertThat(comments.getUser().getId()).isEqualTo(created.getUser().getId());

        CommentFixture.getById(0,token).then()
                .statusCode(404);
    }



    @Test
    void deleteComment() {
        var token = TokenFixture.userToken();

        var postRequest = PostFixture.postToPostRequest(globalObject.validPost);
        postRequest.user_id = 3;
        var post = PostFixture.create(postRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        var request = CommentFixture.commentToCommentRequest(globalObject.validComment);
        request.post_id = post.getId();
        request.user_id = 3;
        var created = CommentFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", CommentResponse.class);

        CommentFixture.deleteById(created.getId(), token).then()
                .statusCode(204);
        CommentFixture.deleteById(created.getId(), token).then()
                .statusCode(404);

    }
}