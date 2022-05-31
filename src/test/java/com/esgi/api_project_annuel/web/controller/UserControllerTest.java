package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.GroupFixture;
import com.esgi.api_project_annuel.web.controller.fixture.UserFixture;
import com.esgi.api_project_annuel.web.controller.fixture.UserFixture;
import com.esgi.api_project_annuel.web.response.GroupResponse;
import com.esgi.api_project_annuel.web.response.UserResponse;
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

        UserFixture.deleteById(response.getId()).then()
                .statusCode(202);
    }

    @Test
    void getByPseudo() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        UserFixture.getByPseudo(request.pseudo).then()
                .statusCode(200);
        request.pseudo = "not found";
        UserFixture.getByPseudo(request.pseudo).then()
                .statusCode(400);
        UserFixture.deleteById(response.getId()).then()
                .statusCode(202);
    }

    @Test
    void getByEmail() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        UserFixture.getByEmail(request.email).then()
                .statusCode(200);
        request.email = "not found";
        UserFixture.getByEmail(request.email).then()
                .statusCode(400);
        UserFixture.deleteById(response.getId()).then()
                .statusCode(202);
    }

    @Test
    void getUserAll() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        var responseALL  = UserFixture.getAll().then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", UserResponse.class);
        assertThat(responseALL).isNotEmpty();
        UserFixture.deleteById(response.getId()).then()
                .statusCode(202);
    }

    @Test
    void getUserById() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        UserFixture.getById(response.getId()).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        UserFixture.getById(0).then()
                .statusCode(400);
        UserFixture.deleteById(response.getId()).then()
                .statusCode(202);
    }



    @Test
    void changePassword() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        request.password = "NewPassword";
        response = UserFixture.changePassword(response.getId(),request).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        request.password = "";
        UserFixture.changePassword(response.getId(),request).then()
                .statusCode(400);
        UserFixture.deleteById(response.getId()).then()
                .statusCode(202);
    }
/*
    @Test
    void changeEmail() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        UserFixture.changeEmail(response.getId(),request).then()
                .statusCode(400); //email exist

        request.email = "ljehanno@myges.fr";
        response = UserFixture.changeEmail(response.getId(),request).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        assertThat(response.getEmail()).isEqualTo(request.email);

        request.email = "";
        UserFixture.changeEmail(response.getId(),request).then()
                .statusCode(400);
                UserFixture.deleteById(response.getId()).then()
                .statusCode(202);
    }

    @Test
    void changePseudo() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        request.pseudo = "NewPseudo";
        response = UserFixture.changePseudo(response.getId(),request).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        assertThat(response.getPseudo()).isEqualTo(request.pseudo);

        UserFixture.changePseudo(response.getId(),request).then()
                .statusCode(400); // pseudo exist

        request.pseudo = "";
        UserFixture.changePseudo(response.getId(),request).then()
                .statusCode(400);
                UserFixture.deleteById(response.getId()).then()
                .statusCode(202);
    }

    @Test
    void changeLastname() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        request.lastname = "NewName";
        response = UserFixture.changeLastname(response.getId(),request).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        assertThat(response.getLastname()).isEqualTo(request.lastname);

        request.lastname = "";
        UserFixture.changeLastname(response.getId(),request).then()
                .statusCode(400);
                UserFixture.deleteById(response.getId()).then()
                .statusCode(202);
    }

    @Test
    void changeFirstname() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        request.firstname = "NewName";
        response = UserFixture.changeFirstname(response.getId(),request).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserResponse.class);
        assertThat(response.getFirstname()).isEqualTo(request.firstname);

        request.firstname = "";
        UserFixture.changeFirstname(response.getId(),request).then()
                .statusCode(400);
                UserFixture.deleteById(response.getId()).then()
                .statusCode(202);
    }
*/
    @Test
    void deleteUser() {
        var request = UserFixture.userToUserRequest(globalObject.validUser);
        var response = UserFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        UserFixture.deleteById(response.getId()).then()
                .statusCode(202);

        UserFixture.deleteById(response.getId()).then()
                .statusCode(400);
    }

    @Test
    void refreshToken() {
    }
}