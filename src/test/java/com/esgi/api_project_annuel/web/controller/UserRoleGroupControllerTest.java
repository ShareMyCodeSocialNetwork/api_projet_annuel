package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.UserRoleGroup;
import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.GroupFixture;
import com.esgi.api_project_annuel.web.controller.fixture.TokenFixture;
import com.esgi.api_project_annuel.web.controller.fixture.UserRoleGroupFixture;
import com.esgi.api_project_annuel.web.request.UserRoleGroupRequest;
import com.esgi.api_project_annuel.web.response.GroupResponse;
import com.esgi.api_project_annuel.web.response.UserRoleGroupResponse;
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
class UserRoleGroupControllerTest {
    @LocalServerPort
    int port;
    GlobalObject globalObject = new GlobalObject();

    @BeforeEach
    void setup(){
        RestAssured.port = port;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    void addUserRoleGroup() {
        var token = TokenFixture.userToken();
        var request = new UserRoleGroupRequest();
        request.user_id = 3;
        request.role_id = 1;
        var groupRequest = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var group = GroupFixture.create(groupRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);
        request.group_id = group.getId();

        var created = UserRoleGroupFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserRoleGroupResponse.class);
        assertThat(created.user.getId()).isEqualTo(request.user_id);
        assertThat(created.role.getId()).isEqualTo(request.role_id);
        assertThat(created.group.getId()).isEqualTo(request.group_id);

        request.user_id = 0;
        request.role_id = 0;
        request.group_id = 0;
        UserRoleGroupFixture.create(request,token).then()
                .statusCode(400);
    }

    @Test
    void getAll() {
        var token = TokenFixture.userToken();
        var request = new UserRoleGroupRequest();
        request.user_id = 3;
        request.role_id = 1;
        var groupRequest = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var group = GroupFixture.create(groupRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);
        request.group_id = group.getId();

        UserRoleGroupFixture.create(request,token).then()
                .statusCode(201);

        var userRoleGroupS = UserRoleGroupFixture.getAll(token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".",UserRoleGroupResponse.class);
        assertThat(userRoleGroupS).isNotEmpty();

    }

    @Test
    void getUserRoleGroupById() {
        var token = TokenFixture.userToken();
        var request = new UserRoleGroupRequest();
        request.user_id = 3;
        request.role_id = 1;
        var groupRequest = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var group = GroupFixture.create(groupRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);
        request.group_id = group.getId();

        var created = UserRoleGroupFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserRoleGroupResponse.class);

        var userRoleGroup = UserRoleGroupFixture.getById(created.getId(),token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserRoleGroupResponse.class);
        assertThat(userRoleGroup.user.getId()).isEqualTo(created.user.getId());
        assertThat(userRoleGroup.role.getId()).isEqualTo(created.role.getId());
        assertThat(userRoleGroup.group.getId()).isEqualTo(created.group.getId());
        assertThat(userRoleGroup.getId()).isEqualTo(created.getId());

        UserRoleGroupFixture.getById(0,token).then()
                .statusCode(404);
    }

    @Test
    void getUserRoleGroupByGroup() {
        var token = TokenFixture.userToken();
        var request = new UserRoleGroupRequest();
        request.user_id = 3;
        request.role_id = 1;
        var groupRequest = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var group = GroupFixture.create(groupRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);
        request.group_id = group.getId();

        UserRoleGroupFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserRoleGroupResponse.class);

        var userRoleGroupS = UserRoleGroupFixture.getByGroup(request.group_id,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", UserRoleGroupResponse.class);
        assertThat(userRoleGroupS).isNotEmpty();
    }

    @Test
    void getUserRoleGroupByUser() {
        var token = TokenFixture.userToken();
        var request = new UserRoleGroupRequest();
        request.user_id = 3;
        request.role_id = 1;
        var groupRequest = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var group = GroupFixture.create(groupRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);
        request.group_id = group.getId();

        UserRoleGroupFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserRoleGroupResponse.class);

        var userRoleGroupS = UserRoleGroupFixture.getByUser(request.user_id,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", UserRoleGroupResponse.class);
        assertThat(userRoleGroupS).isNotEmpty();
    }

    @Test
    void getUserRoleGroupByGroupAndUser() {
        var token = TokenFixture.userToken();
        var request = new UserRoleGroupRequest();
        request.user_id = 3;
        request.role_id = 1;
        var groupRequest = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var group = GroupFixture.create(groupRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);
        request.group_id = group.getId();

        UserRoleGroupFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserRoleGroupResponse.class);

        var userRoleGroup = UserRoleGroupFixture.getByGroupAndUser(request.group_id,request.user_id,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserRoleGroupResponse.class);
        assertThat(userRoleGroup.getGroup().getId()).isEqualTo(request.group_id);
        assertThat(userRoleGroup.getRole().getId()).isEqualTo(request.role_id);
        assertThat(userRoleGroup.getUser().getId()).isEqualTo(request.user_id);

        UserRoleGroupFixture.getByGroupAndUser(0,0,token).then()
                .statusCode(404);
    }

    @Test
    void deleteUserRoleGroup() {
        var token = TokenFixture.userToken();
        var request = new UserRoleGroupRequest();
        request.user_id = 3;
        request.role_id = 1;
        var groupRequest = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        var group = GroupFixture.create(groupRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);
        request.group_id = group.getId();

        var created = UserRoleGroupFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserRoleGroupResponse.class);

        UserRoleGroupFixture.deleteById(created.getId(), token).then()
                .statusCode(204);
        UserRoleGroupFixture.deleteById(created.getId(), token).then()
                .statusCode(404);
    }
}