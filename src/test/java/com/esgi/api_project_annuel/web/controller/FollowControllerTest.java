package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.FollowFixture;
import com.esgi.api_project_annuel.web.controller.fixture.TokenFixture;
import com.esgi.api_project_annuel.web.controller.fixture.UserFixture;
import com.esgi.api_project_annuel.web.request.FollowRequest;
import com.esgi.api_project_annuel.web.response.FollowResponse;
import com.esgi.api_project_annuel.web.response.UserResponse;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class FollowControllerTest {
    @LocalServerPort
    int port;
    GlobalObject globalObject = new GlobalObject();

    @BeforeEach
    void setup(){
        RestAssured.port = port;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
    @Test
    void addFollow() {
        var token = TokenFixture.userToken();

        var request = new FollowRequest();
        request.followerUserId = 3;
        request.followedUserId = 3;
        var response = FollowFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", FollowResponse.class);

        assertThat(response.followed.getId()).isEqualTo(request.followedUserId);
        assertThat(response.follower.getId()).isEqualTo(request.followerUserId );

       FollowFixture.create(request,token).then()
                .statusCode(400); //exist dejq

        FollowFixture.deleteById(response.getId(),token);
    }

    @Test
    void getFollowAll() {
        var token = TokenFixture.userToken();
        var responseALL  = FollowFixture.getAll(token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FollowResponse.class);
        assertThat(responseALL).isNotEmpty();
    }

    @Test
    void getFollowers() {
        var token = TokenFixture.userToken();

        var user1 = UserFixture.userToUserRequest(globalObject.buildValidUser());
        var user1Created = UserFixture.create(user1).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var user2 = UserFixture.userToUserRequest(globalObject.buildValidUser());
        var user2Created = UserFixture.create(user2).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var request = new FollowRequest();
        request.followerUserId = user1Created.getId();
        request.followedUserId = user2Created.getId();
        FollowFixture.create(request,token).then()
                .statusCode(201);

        var responseALL  = FollowFixture.getFollower(user2Created.getId(), token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FollowResponse.class);
        assertThat(responseALL).isNotEmpty();
    }

    @Test
    void getFollowed() {
        var token = TokenFixture.userToken();
        var responseALL  = FollowFixture.getFollowed(0,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FollowResponse.class);
        assertThat(responseALL).isEmpty();
    }

    @Test
    void getFollowById() {
        var token = TokenFixture.userToken();
         FollowFixture.getById(0,token).then()
                .statusCode(404);
        var user1 = UserFixture.userToUserRequest(globalObject.buildValidUser());
        var user1Created = UserFixture.create(user1).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var user2 = UserFixture.userToUserRequest(globalObject.buildValidUser());
        var user2Created = UserFixture.create(user2).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var request = new FollowRequest();
        request.followerUserId = user1Created.getId();
        request.followedUserId = user2Created.getId();
        var response = FollowFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", FollowResponse.class);

        FollowFixture.getById(response.getId(), token).then()
                .statusCode(200);
    }


    @Test
    void deleteFollow() {
        var token = TokenFixture.userToken();

        var user1 = UserFixture.userToUserRequest(globalObject.buildValidUser());
        var user1Created = UserFixture.create(user1).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var user2 = UserFixture.userToUserRequest(globalObject.buildValidUser());
        var user2Created = UserFixture.create(user2).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var request = new FollowRequest();
        request.followerUserId = user1Created.getId();
        request.followedUserId = user2Created.getId();
        var follow = FollowFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", FollowResponse.class);

        var getFollow = FollowFixture.getByFollowedAndFollower(request, token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", FollowResponse.class);

        assertThat(follow.followed.getId()).isEqualTo(getFollow.getFollowed().getId());
        assertThat(follow.follower.getId()).isEqualTo(getFollow.getFollower().getId());

        FollowFixture.deleteById(follow.getId(),token).then()
                .statusCode(204);

        FollowFixture.deleteById(0,token).then()
            .statusCode(400);

        FollowFixture.getByFollowedAndFollower(request, token).then()
                .statusCode(404);

    }
}