package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.response.RoleResponse;
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
class RoleControllerTest {

    @LocalServerPort
    int port;
    GlobalObject globalObject = new GlobalObject();

    @BeforeEach
    void setup(){
        RestAssured.port = port;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    void addRole() {
        var request = RoleFixture.roleToRoleRequest(globalObject.validRole);
        RoleFixture.create(request).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", RoleResponse.class);
        request.name = "";
        RoleFixture.create(request).then()
                .statusCode(400);

    }

    @Test
    void getAll() {
        var request = RoleFixture.roleToRoleRequest(globalObject.validRole);
        RoleFixture.create(request).then()
                .statusCode(200);
        var response  = RoleFixture.getAll().then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", RoleResponse.class);
        assertThat(response).isNotEmpty();
    }

    @Test
    void getRoleById() {
        var request = RoleFixture.roleToRoleRequest(globalObject.validRole);
        var response = RoleFixture.create(request).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", RoleResponse.class);

        RoleFixture.getById(response.id).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", RoleResponse.class);

        RoleFixture.getById(0).then()
                .statusCode(400);
    }

    @Test
    void getRoleByName() {
        var request = RoleFixture.roleToRoleRequest(globalObject.validRole);
        RoleFixture.create(request).then()
                .statusCode(200);
        RoleFixture.getByName(request.name).then()
                .statusCode(200);
    }

    @Test
    void changeContent() {
        var request = RoleFixture.roleToRoleRequest(globalObject.validRole);
        var response = RoleFixture.create(request).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", RoleResponse.class);
        request.name = "NewName";
        response = RoleFixture.changeName(response.id,request).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", RoleResponse.class);
        assertThat(response.name).isEqualTo(request.name);

        request.name = "";
         RoleFixture.changeName(response.id,request).then()
                .statusCode(400);
    }

    @Test
    void deleteRole() {
        var request = RoleFixture.roleToRoleRequest(globalObject.validRole);
        var response = RoleFixture.create(request).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", RoleResponse.class);

        RoleFixture.deleteById(response.id).then()
                .statusCode(200);

        RoleFixture.deleteById(response.getId()).then()
                .statusCode(400);
    }
}