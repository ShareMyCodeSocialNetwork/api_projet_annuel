package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.*;
import com.esgi.api_project_annuel.web.request.FollowRequest;
import com.esgi.api_project_annuel.web.request.LikeRequest;
import com.esgi.api_project_annuel.web.response.*;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Locale;

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

    @Test
    public void should_test_get_all_followed_user_post(){
        var userRequest1 = UserFixture.userToUserRequest(globalObject.buildValidUser());

        var userResponse1 = UserFixture.create(userRequest1).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        var token1 = TokenFixture.getToken(userRequest1);

        var userRequest2 = UserFixture.userToUserRequest(globalObject.buildValidUser());

        var userResponse2 = UserFixture.create(userRequest2).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        var token2 = TokenFixture.getToken(userRequest2);

        var userRequest3 = UserFixture.userToUserRequest(globalObject.buildValidUser());
        var userResponse3 = UserFixture.create(userRequest3).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        var token3 = TokenFixture.getToken(userRequest2);

        var postRequest = PostFixture.postToPostRequest(globalObject.buildValidPost());
        postRequest.user_id = userResponse1.getId();
        var postResponse = PostFixture.create(postRequest,token1).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        var postRequest3 = PostFixture.postToPostRequest(globalObject.buildValidPost());
        postRequest3.user_id = userResponse3.getId();
        var postResponse3 = PostFixture.create(postRequest3,token1).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);


        var followRequest = new FollowRequest();
        followRequest.followedUserId = userResponse1.getId();
        followRequest.followerUserId = userResponse2.getId();
        var user2FollowUser1 = FollowFixture.create(followRequest,token2).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", FollowResponse.class);

        var followRequest2 = new FollowRequest();
        followRequest2.followedUserId = userResponse1.getId();
        followRequest2.followerUserId = userResponse3.getId();
        var user3FollowUser1 = FollowFixture.create(followRequest2,token3).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", FollowResponse.class);

        var allFollowedUsersPostsOfUser2 = PostFixture.getFullFollowedUserPosts(userResponse2.getId(), token2).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);

        assertThat(allFollowedUsersPostsOfUser2.size()).isEqualTo(1);
        assertThat(allFollowedUsersPostsOfUser2.get(0).getPost().getId()).isEqualTo(postResponse.getId());

        var allFollowedUsersPostsOfUser1 = PostFixture.getFullFollowedUserPosts(userResponse1.getId(), token1).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);

        assertThat(allFollowedUsersPostsOfUser1.size()).isEqualTo(0);

    }

    @Test
    public void should_test_levenshtein_search_posts(){
        var reqUser1 = UserFixture.userToUserRequest(globalObject.buildValidUser());
        reqUser1.firstname = GlobalObject.randomPseudo();
        reqUser1.lastname = GlobalObject.randomPseudo();
        var resUser1 = UserFixture.create(reqUser1).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        var token = TokenFixture.getToken(reqUser1);

        var levenshteinSearchPost = PostFixture.searchLevenshtein("qqchose qui nexiste pas", token)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);
        assertThat(levenshteinSearchPost.size()).isEqualTo(0);

        var reqCode1 = CodeFixture.codeToCodeRequest(globalObject.validCode);
        reqCode1.user_id = resUser1.getId();
        reqCode1.language_id = 1;
        reqCode1.name = GlobalObject.randomPseudo();
        var resCode1 = CodeFixture.create(reqCode1,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Code.class);

        var reqCode2 = CodeFixture.codeToCodeRequest(globalObject.validCode);
        reqCode2.user_id = resUser1.getId();
        reqCode2.language_id = 2;
        reqCode2.name = GlobalObject.randomPseudo();
        var resCode2 = CodeFixture.create(reqCode2,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Code.class);

        var reqPost1 = PostFixture.postToPostRequest(globalObject.buildValidPost());
        reqPost1.user_id = resUser1.getId();
        reqPost1.code_id = resCode1.getId();
        reqPost1.content = GlobalObject.randomPseudo();
        var postResponse1 = PostFixture.create(reqPost1,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);



        var reqPost2 = PostFixture.postToPostRequest(globalObject.buildValidPost());
        reqPost2.user_id = resUser1.getId();
        reqPost2.code_id = resCode2.getId();
        reqPost2.content = GlobalObject.randomPseudo();
        var resPost2 = PostFixture.create(reqPost2,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", PostResponse.class);

        // pass user info
        var searchPost = PostFixture.searchLevenshtein( resUser1.getEmail(), token)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);
        assertThat(searchPost.size()).isEqualTo(2);

        searchPost = PostFixture.searchLevenshtein( resUser1.getFirstname(), token)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);
        assertThat(searchPost.size()).isEqualTo(2);

        searchPost = PostFixture.searchLevenshtein( resUser1.getPseudo(), token)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);
        assertThat(searchPost.size()).isEqualTo(2);

        searchPost = PostFixture.searchLevenshtein( resUser1.getLastname(), token)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);
        assertThat(searchPost.size()).isEqualTo(2);


        //pass postContent
        searchPost = PostFixture.searchLevenshtein(resPost2.content.toUpperCase(), token)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);
        assertThat(searchPost.size()).isEqualTo(1);

        searchPost = PostFixture.searchLevenshtein(resPost2.content.toUpperCase(), token)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);
        assertThat(searchPost.size()).isEqualTo(1);

        //code info
        searchPost = PostFixture.searchLevenshtein(resCode1.getNameCode().toLowerCase(), token)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);
        assertThat(searchPost.size()).isEqualTo(1);

        searchPost = PostFixture.searchLevenshtein(resCode1.getLanguage().getName().toUpperCase(), token)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FullPostResponse.class);
        assertThat(searchPost.size()).isEqualTo(1);



    }
}