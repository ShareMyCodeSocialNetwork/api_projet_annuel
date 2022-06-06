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
        var user1 = UserFixture.getById(1,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        var user2 = UserFixture.getById(3,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var request = new FollowRequest();
        request.followerUserId = user1.getId();
        request.followedUserId = user2.getId();
        var response = FollowFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", FollowResponse.class);

        assertThat(response.followed.getId()).isEqualTo(request.followedUserId);
        assertThat(response.follower.getId()).isEqualTo(request.followerUserId );
    }

    @Test
    void getFollowAll() {
        var token = TokenFixture.userToken();
        var responseALL  = FollowFixture.getAll(token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FollowResponse.class);
        assertThat(responseALL).isEmpty();
    }

    @Test
    void getFollowers() {
        var token = TokenFixture.userToken();
        var responseALL  = FollowFixture.getFollower(0,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FollowResponse.class);
        assertThat(responseALL).isEmpty();
    }

    @Test
    void getFollowed() {
        var token = TokenFixture.userToken();
        var responseALL  = FollowFixture.getFollowed(0,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", FollowResponse.class);
        assertThat(responseALL).isEmpty();
        //todo voir avec un create une fois que ca fonctionneras
    }

    @Test
    void getFollowById() {
        var token = TokenFixture.userToken();
         FollowFixture.getById(0,token).then()
                .statusCode(404);
    }


    @Test
    void deleteFollow() {
        var token = TokenFixture.userToken();
        var user1 = UserFixture.getById(1,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        var user2 = UserFixture.getById(3,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var request = new FollowRequest();
        request.followerUserId = user2.getId();
        request.followedUserId = user1.getId();

        var response = FollowFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", FollowResponse.class);

        FollowFixture.deleteById(response.getId(),token).then()
                .statusCode(202);

        FollowFixture.deleteById(0,token).then()
            .statusCode(400);

    }
}