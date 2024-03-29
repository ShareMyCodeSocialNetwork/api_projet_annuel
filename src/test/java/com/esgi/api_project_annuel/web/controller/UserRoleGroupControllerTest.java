package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.UserRoleGroup;
import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.*;
import com.esgi.api_project_annuel.web.request.RoleRequest;
import com.esgi.api_project_annuel.web.request.UserRoleGroupRequest;
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
        groupRequest.user_id = 3;
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
        groupRequest.user_id = 3;
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
        groupRequest.user_id = 3;
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
        groupRequest.user_id = 3;
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
        groupRequest.user_id = 3;
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
        groupRequest.user_id = 3;
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
        groupRequest.user_id = 3;
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

    @Test
    void should_delete_link(){
        var token = TokenFixture.userToken();
        var request = new UserRoleGroupRequest();
        request.user_id = 3;
        request.role_id = 1;
        var groupRequest = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        groupRequest.user_id = 3;
        var group = GroupFixture.create(groupRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);
        request.group_id = group.getId();

        var created = UserRoleGroupFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserRoleGroupResponse.class);

        GroupFixture.deleteById(group.getId(), token).then().statusCode(204);

        UserRoleGroupFixture.getById(created.getId(), token).then()
                .statusCode(404);

        GroupFixture.getById(group.getId(), token).then().statusCode(404);
    }

    @Test
    void should_delete_link2(){
        var token = TokenFixture.userToken();
        var adminToken = TokenFixture.adminToken();

        var roleRequest = new RoleRequest();
        roleRequest.name = "test";
        var role = RoleFixture.create(roleRequest, adminToken).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", RoleResponse.class);
        assertThat(role.name).isEqualTo(roleRequest.name);

        var groupRequest = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        groupRequest.user_id = 3;
        var group = GroupFixture.create(groupRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        var request = new UserRoleGroupRequest();
        request.user_id = 3;
        request.role_id = role.getId();
        request.group_id = group.getId();

        var created  = UserRoleGroupFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserRoleGroupResponse.class);

        var updated = UserRoleGroupFixture.changeUserRoleInGroup(created.getId(), request, adminToken).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserRoleGroupResponse.class);
        assertThat(updated.getRole().getTitlePermission()).isEqualTo(role.getName());

        RoleFixture.deleteById(role.getId(), adminToken).then()
                .statusCode(200);

        RoleFixture.getById(role.getId(), adminToken).then()
                .statusCode(400);
        UserRoleGroupFixture.getById(updated.getId(), token).then()
                .statusCode(200);

        var get = UserRoleGroupFixture.getById(updated.getId(), token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", UserRoleGroupResponse.class);

        assertThat(get.getRole().getTitlePermission()).isEqualTo("USER");
    }

    @Test
    public void should_test_get_full_userrolegroup(){
        var userRequest = UserFixture.userToUserRequest(globalObject.buildValidUser());
        var userResponse = UserFixture.create(userRequest).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var userRequest2 = UserFixture.userToUserRequest(globalObject.buildValidUser());
        var userResponse2 = UserFixture.create(userRequest2).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserResponse.class);

        var token = TokenFixture.getToken(userRequest);

        var groupRequest = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        groupRequest.user_id = userResponse.getId();
        var groupResponse = GroupFixture.create(groupRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        var roleRequest = new RoleRequest();
        roleRequest.name = "USER";
        var roleResponse = RoleFixture.getByName("USER", token)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", RoleResponse.class);

        var userRoleGroupRequest = new UserRoleGroupRequest();
        userRoleGroupRequest.group_id = groupResponse.getId();
        userRoleGroupRequest.role_id = roleResponse.getId();
        userRoleGroupRequest.user_id = userResponse2.getId();
        var userRoleGroupResponse = UserRoleGroupFixture.create(userRoleGroupRequest, token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", UserRoleGroupResponse.class);

        var fullResponse = UserRoleGroupFixture.getFullResponse(token, groupResponse.getId(), userResponse.getId())
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", FullUserRoleGroupResponse.class);

        assertThat(fullResponse.getIsInGroup()).isEqualTo(null);
        assertThat(fullResponse.getUserInGroupWithRole().size()).isEqualTo(1);

        var fullResponse2 = UserRoleGroupFixture.getFullResponse(token, groupResponse.getId(), userResponse2.getId())
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", FullUserRoleGroupResponse.class);

        assertThat(fullResponse2.getIsInGroup().getGroup().getId()).isEqualTo(userRoleGroupResponse.getGroup().getId());
        assertThat(fullResponse2.getIsInGroup().getUser().getId()).isEqualTo(userRoleGroupResponse.getUser().getId());
        assertThat(fullResponse2.getIsInGroup().getRole().getId()).isEqualTo(userRoleGroupResponse.getRole().getId());
        assertThat(fullResponse2.getUserInGroupWithRole().size()).isEqualTo(1);
    }
}