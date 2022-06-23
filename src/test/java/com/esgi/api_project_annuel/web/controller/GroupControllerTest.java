package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.GroupFixture;
import com.esgi.api_project_annuel.web.controller.fixture.ProjectFixture;
import com.esgi.api_project_annuel.web.controller.fixture.TokenFixture;
import com.esgi.api_project_annuel.web.response.GroupResponse;
import com.esgi.api_project_annuel.web.response.ProjectResponse;
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
        var token = TokenFixture.userToken();
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        GroupFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);
        request.name = "";
        GroupFixture.create(request,token).then()
                .statusCode(400);
    }

    @Test
    void getGroupAll() {
        var token = TokenFixture.userToken();
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        GroupFixture.create(request,token).then()
                .statusCode(201);
        var response  = GroupFixture.getAll(token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", GroupResponse.class);
        assertThat(response).isNotEmpty();
    }

    @Test
    void getGroupByName() {
        var token = TokenFixture.userToken();
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        GroupFixture.create(request,token).then()
                .statusCode(201);
        GroupFixture.getByName(request.name,token).then()
                .statusCode(200);
    }

    @Test
    void getGroupById() {
        var token = TokenFixture.userToken();
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var response = GroupFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        GroupFixture.getById(response.id,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        GroupFixture.getById(0,token).then()
                .statusCode(404);
    }

    @Test
    void changeName() {
        var token = TokenFixture.userToken();
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var response = GroupFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        GroupFixture.changeName(0,request,token).then()
                .statusCode(404);

        request.name = "NewName";
        response = GroupFixture.changeName(response.id,request,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);
        assertThat(response.name).isEqualTo(request.name);

        request.name = "";
        GroupFixture.changeName(response.id,request,token).then()
                .statusCode(400);
    }

    @Test
    void changeDescription() {
        var token = TokenFixture.userToken();
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var response = GroupFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        GroupFixture.changeDescription(0, request, token).then()
                .statusCode(404);

        request.description = "NewDescription";
        response = GroupFixture.changeDescription(response.id,request,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);
        assertThat(response.name).isEqualTo(request.name);

        request.description = "";
        GroupFixture.changeDescription(response.id,request,token).then()
                .statusCode(400);
    }

    @Test
    void updateGroup() {
        var token = TokenFixture.userToken();
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var response = GroupFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        GroupFixture.update(0,request,token).then()
                .statusCode(400);

        request.name = "NewName";
        response = GroupFixture.update(response.id,request,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);
        assertThat(response.name).isEqualTo(request.name);

        request.name = "";
        GroupFixture.update(response.id,request,token).then()
                .statusCode(400);
    }

    @Test
    void deleteGroup() {
        var token = TokenFixture.userToken();
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var response = GroupFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        GroupFixture.deleteById(response.id,token).then()
                .statusCode(204);

        GroupFixture.deleteById(response.getId(),token).then()
                .statusCode(400);
    }

    @Test
    void should_delete_link(){
        var token = TokenFixture.userToken();
        var request = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var group = GroupFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);


        var projectRequest = ProjectFixture.projectToProjectRequest(globalObject.validProject);


        projectRequest.group_id = group.id;
        projectRequest.user_id = 3;

        var project = ProjectFixture.create(projectRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        GroupFixture.deleteById(group.getId(), token).then().statusCode(204);
        GroupFixture.getById(group.getId(), token).then().statusCode(404);
        ProjectFixture.getById(project.getId(), token).then().statusCode(404);

    }
}