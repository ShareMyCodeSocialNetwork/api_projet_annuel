package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.GroupFixture;
import com.esgi.api_project_annuel.web.response.GroupResponse;
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
class GroupControllerTest {

    @LocalServerPort
    int port;
    GlobalObject globalObject = new GlobalObject();

    @BeforeEach
    void setup(){
        RestAssured.port = port;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
    @Test
    void addGroup() {
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        GroupFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);
        request.name = "";
        GroupFixture.create(request).then()
                .statusCode(400);
    }

    @Test
    void getGroupAll() {
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        GroupFixture.create(request).then()
                .statusCode(201);
        var response  = GroupFixture.getAll().then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", GroupResponse.class);
        assertThat(response).isNotEmpty();
    }

    @Test
    void getRoleByName() {
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        GroupFixture.create(request).then()
                .statusCode(201);
        GroupFixture.getByName(request.name).then()
                .statusCode(200);
    }

    @Test
    void getGroupById() {
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var response = GroupFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        GroupFixture.getById(response.id).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        GroupFixture.getById(0).then()
                .statusCode(404);
    }

    @Test
    void changeName() {
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var response = GroupFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        GroupFixture.changeName(0,request).then()
                .statusCode(404);

        request.name = "NewName";
        response = GroupFixture.changeName(response.id,request).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);
        assertThat(response.name).isEqualTo(request.name);

        request.name = "";
        GroupFixture.changeName(response.id,request).then()
                .statusCode(400);
    }

    @Test
    void updateGroup() {
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var response = GroupFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        GroupFixture.update(0,request).then()
                .statusCode(400);

        request.name = "NewName";
        response = GroupFixture.update(response.id,request).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);
        assertThat(response.name).isEqualTo(request.name);

        request.name = "";
        GroupFixture.changeName(response.id,request).then()
                .statusCode(400);
    }

    @Test
    void deleteGroup() {
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var response = GroupFixture.create(request).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        GroupFixture.deleteById(response.id).then()
                .statusCode(204);

        GroupFixture.deleteById(response.getId()).then()
                .statusCode(400);
    }
}