package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.Domain.entities.Project;
import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.*;
import com.esgi.api_project_annuel.web.response.FullProjectResponse;
import com.esgi.api_project_annuel.web.response.GroupResponse;
import com.esgi.api_project_annuel.web.response.ProjectResponse;
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
class ProjectControllerTest {

    @LocalServerPort
    int port;
    GlobalObject globalObject = new GlobalObject();

    @BeforeEach
    void setup(){
        RestAssured.port = port;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    void addProject() {
        var token = TokenFixture.userToken();
        var request = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        var groupRequest = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        groupRequest.user_id = 3;
        var group = GroupFixture.create(groupRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        request.group_id = group.id;
        request.user_id = 3;

        ProjectFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        request.user_id = 0;

        ProjectFixture.create(request,token).then()
                .statusCode(400);

        //project without group

        request.group_id = 0;// exist pas
        request.user_id = 3;

        var project = ProjectFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);
        assertThat(project.name).isEqualTo(request.name);

    }

    @Test
    void getAll() {
        var token = TokenFixture.userToken();

        var request = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        request.user_id = 3;
        ProjectFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);


        var projects = ProjectFixture.getAll(token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", ProjectResponse.class);
        assertThat(projects).isNotEmpty();
    }

    @Test
    void getProjectById() {
        var token = TokenFixture.userToken();
        ProjectFixture.getById(0,token).then()
                .statusCode(404);

        var request = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        request.user_id = 3;
        var project = ProjectFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        ProjectFixture.getById(project.getId(),token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

    }

    @Test
    void getProjectByName() {
        var token = TokenFixture.userToken();

        var request = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        request.user_id = 3;

        var project = ProjectFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        var projects = ProjectFixture.getByName(project.name,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", ProjectResponse.class);
        assertThat(projects).isNotEmpty();
    }

    @Test
    void getProjectByGroup() {
        var token = TokenFixture.userToken();

        var request = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        var groupRequest = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        groupRequest.user_id = 3;
        var group = GroupFixture.create(groupRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        request.group_id = group.id;
        request.user_id = 3;

        var project = ProjectFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        var projects = ProjectFixture.getByGroup(project.group.getId(),token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", ProjectResponse.class);

        assertThat(projects).isNotEmpty();
    }

    @Test
    void getProjectByOwner() {
        var token = TokenFixture.userToken();

        var request = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        request.user_id = 3;

        var project = ProjectFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        var projects = ProjectFixture.getByUser(project.user.getId(),token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", ProjectResponse.class);
        assertThat(projects).isNotEmpty();
    }

    @Test
    void changeGroup() {
        var token = TokenFixture.userToken();

        var request = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        request.user_id = 3;
        var response = ProjectFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        var groupRequest = GroupFixture.groupToGroupRequest(globalObject.validGroup);
        groupRequest.user_id = 3;
        var group = GroupFixture.create(groupRequest,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", GroupResponse.class);

        request.group_id = group.id;

        response = ProjectFixture.changeGroup(response.getId(),request,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);


        request.group_id = 0;
        ProjectFixture.changeGroup(response.getId(),request,token).then()
                .statusCode(400);
    }

    @Test
    void changeOwner() {
        var token = TokenFixture.userToken();

        var request = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        request.user_id = 3;
        var response = ProjectFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        request.user_id = 1;

        response = ProjectFixture.changeOwner(response.getId(),request,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);


        request.group_id = 0;
        ProjectFixture.changeOwner(response.getId(),request,token).then()
                .statusCode(400);
    }

    @Test
    void changeName() {
        var token = TokenFixture.userToken();

        var request = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        request.user_id = 3;
        var response = ProjectFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        request.name = "newName";

        response = ProjectFixture.changeName(response.getId(),request,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);


        request.name = "";
        ProjectFixture.changeName(response.getId(),request,token).then()
                .statusCode(400);
    }


    @Test
    void changeDescription() {
        var token = TokenFixture.userToken();

        var request = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        request.user_id = 3;
        var response = ProjectFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        request.description = "newDescription";

        response = ProjectFixture.changeDescription(response.getId(),request,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);


        request.description = "";
        ProjectFixture.changeDescription(response.getId(),request,token).then()
                .statusCode(400);
    }

    @Test
    void deleteProject() {
        var token = TokenFixture.userToken();

        var request = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        request.user_id = 3;
        var response = ProjectFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);
        ProjectFixture.deleteById(response.getId(),token).then()
                .statusCode(204);
        ProjectFixture.deleteById(response.getId(),token).then()
                .statusCode(404);
    }


    @Test
    void should_set_code_project_to_null() {
        var request = CodeFixture.codeToCodeRequest(globalObject.validCode);
        var token = TokenFixture.userToken();

        var project = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        project.user_id = 3;
        var createdProject = ProjectFixture.create(project,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        request.user_id = 3;
        request.language_id = 1;
        request.project_id = createdProject.id;
        var createdCode = CodeFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Code.class);

        ProjectFixture.deleteById(createdProject.getId(),token).then()
                .statusCode(204);
        ProjectFixture.deleteById(createdProject.getId(),token).then()
                .statusCode(404);

        ProjectFixture.getById(createdProject.getId(),token).then()
                .statusCode(404);
        CodeFixture.getById(createdCode.getId(), token).then()
                        .statusCode(404);


        /*var getCodeByProject = CodeFixture.getByProject(createdProject.getId(), token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", Code.class);
        assertThat(getCodeByProject.size()).isEqualTo(0);*/
    }


    @Test
    public void should_test_get_full_project(){
        var request = CodeFixture.codeToCodeRequest(globalObject.validCode);
        var token = TokenFixture.userToken();

        var project = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        project.user_id = 3;
        var createdProject = ProjectFixture.create(project,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        request.user_id = 3;
        request.language_id = 1;
        request.project_id = createdProject.id;
        CodeFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Code.class);
        CodeFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Code.class);

        var getFullProject = ProjectFixture.getByIdFull(createdProject.getId(), token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", FullProjectResponse.class);
        assertThat(getFullProject.getProject().getId()).isEqualTo(createdProject.getId());
        assertThat(getFullProject.getCodesInProject().size()).isEqualTo(2);


        CodeFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Code.class);
        getFullProject = ProjectFixture.getByIdFull(createdProject.getId(), token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", FullProjectResponse.class);
        assertThat(getFullProject.getCodesInProject().size()).isEqualTo(3);

        ProjectFixture.getByIdFull(0, token).then()
                .statusCode(404);
    }
}