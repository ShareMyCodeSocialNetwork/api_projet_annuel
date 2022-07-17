package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.*;
import com.esgi.api_project_annuel.web.request.LikeRequest;
import com.esgi.api_project_annuel.web.response.*;
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
        request.user_id = 3;
        PostFixture.create(request,token).then()
                .statusCode(406);



    }

    @Test
    void addPostWithCode() {
        var token = TokenFixture.userToken();
        var request = PostFixture.postToPostRequest(globalObject.validPost);
        request.user_id = 3;

        var codeRequest = CodeFixture.codeToCodeRequest(globalObject.validCode);
        codeRequest.user_id = 3;
        codeRequest.language_id = 1;
        var codeCreated = CodeFixture.create(codeRequest, token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Code.class);

        request.code_id = codeCreated.getId();
        var post = PostFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);
        assertThat(post.getUser().getId()).isEqualTo(request.user_id);
        assertThat(post.getCode().getId()).isEqualTo(request.code_id);

        request.user_id = 42000;
        PostFixture.create(request,token).then()
                .statusCode(406);

        request.content = "";
        request.user_id = 3;
        PostFixture.create(request,token).then()
                .statusCode(406);



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

    @Test
    void should_delete_link(){
        var token = TokenFixture.userToken();
        var request = PostFixture.postToPostRequest(globalObject.validPost);
        request.user_id = 3;
        var post = PostFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        var commentRequest = CommentFixture.commentToCommentRequest(globalObject.validComment);
        commentRequest.post_id = post.getId();
        commentRequest.user_id = 3;
        var comment  = CommentFixture.create(commentRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", CommentResponse.class);
        var likeRequest = new LikeRequest();
        likeRequest.post_id = post.getId();
        likeRequest.user_id = 2;

        var like = LikeFixture.create(likeRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", LikeResponse.class);

        PostFixture.deleteById(post.getId(), token).then()
                .statusCode(204);

        PostFixture.getById(post.getId(), token).then().statusCode(404);
        CommentFixture.getById(comment.getId(), token).then().statusCode(404);
        LikeFixture.getById(like.getId(), token).then().statusCode(404);

    }

    @Test
    public void should_get_post_with_comments_and_likes(){
        var initialSize = PostFixture.getAllFull(TokenFixture.userToken()).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);

        /** create first user  **/
        var userRequest = UserFixture.userToUserRequest(globalObject.buildValidUser());

        var userResponse = UserFixture.create(userRequest).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        var token = TokenFixture.getToken(userRequest);
        var initialSizeUser1 = PostFixture.getByUserFull(userResponse.getId(), token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);

        /** first user create post  **/
        var postRequest = PostFixture.postToPostRequest(globalObject.validPost);
        postRequest.user_id = userResponse.getId();

        var postResponse = PostFixture.create(postRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        /** first user create comment**/
        var commentRequest = CommentFixture.commentToCommentRequest(globalObject.validComment);
        commentRequest.post_id = postResponse.getId();
        commentRequest.user_id = userResponse.getId();

        var commentResponse = CommentFixture.create(commentRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", CommentResponse.class);

        /** first user create like  **/
        var likeRequest = new LikeRequest();
        likeRequest.post_id = postResponse.getId();
        likeRequest.user_id = userResponse.getId();

        var likeResponse = LikeFixture.create(likeRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", LikeResponse.class);


        /** create second user  **/
        var userRequest2 = UserFixture.userToUserRequest(globalObject.buildValidUser());

        var userResponse2 = UserFixture.create(userRequest2).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        var token2 = TokenFixture.getToken(userRequest2);

        var initialSizeUser2 = PostFixture.getByUser(userResponse2.getId(), token2).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);

        /** first user create post  **/
        var postRequest2 = PostFixture.postToPostRequest(globalObject.validPost);
        postRequest2.user_id = userResponse2.getId();

        var postResponse2 = PostFixture.create(postRequest2,token2).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        /** first user create comment**/
        var commentRequest2 = CommentFixture.commentToCommentRequest(globalObject.validComment);
        commentRequest2.post_id = postResponse.getId();
        commentRequest2.user_id = userResponse2.getId();

        var commentResponse2 = CommentFixture.create(commentRequest2,token2).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", CommentResponse.class);

        /** first user create like  **/
        var likeRequest2 = new LikeRequest();
        likeRequest2.post_id = postResponse2.getId();
        likeRequest2.user_id = userResponse2.getId();

        var likeResponse2 = LikeFixture.create(likeRequest2,token2).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", LikeResponse.class);

        /** get full post by id **/
        var fullPostResponse = PostFixture.getByFullId(postResponse.getId(), token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", FullPostResponse.class);

        var fullPostResponse2 = PostFixture.getByFullId(postResponse2.getId(), token2).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", FullPostResponse.class);


        assertThat(fullPostResponse.getPost().getId()).isEqualTo(postResponse.getId());
        assertThat(fullPostResponse.getLikes()).isNotEmpty();
        assertThat(fullPostResponse.getComments()).isNotEmpty();

        assertThat(fullPostResponse2.getPost().getId()).isEqualTo(postResponse2.getId());
        assertThat(fullPostResponse2.getLikes()).isNotEmpty();
        assertThat(fullPostResponse2.getComments()).isEmpty();


        PostFixture.getByFullId(0, token).then()
                .statusCode(404);


        var posts = PostFixture.getAllFull(token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);
        assertThat(posts.size()).isEqualTo(initialSize.size() + 2);


        var postsUser1 = PostFixture.getByUserFull(userResponse.getId(), token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);
        assertThat(postsUser1.size()).isEqualTo(initialSizeUser1.size() + 1);

        var postsUser2 = PostFixture.getByUserFull(userResponse2.getId() ,token2).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);
        assertThat(postsUser2.size()).isEqualTo(initialSizeUser2.size() + 1);
    }
}