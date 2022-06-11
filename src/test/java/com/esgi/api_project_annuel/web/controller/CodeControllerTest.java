package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.*;
import com.esgi.api_project_annuel.web.request.CodeRequest;
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
class CodeControllerTest {
    @LocalServerPort
    int port;
    GlobalObject globalObject = new GlobalObject();

    @BeforeEach
    void setup(){
        RestAssured.port = port;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    void create() {
        var request = CodeFixture.codeToCodeRequest(globalObject.validCode);
        var token = TokenFixture.userToken();

        var project = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        project.user_id = 3;
        var createdProject = ProjectFixture.create(project,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        request.userId = 3;
        request.language_id = 1;
        request.project_id = createdProject.id;
        CodeFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Code.class);

        request.name = "";
        CodeFixture.create(request,token).then()
                .statusCode(406);
    }

    @Test
    void getAllCodes() {
        var token = TokenFixture.userToken();

        var request = CodeFixture.codeToCodeRequest(globalObject.validCode);
        var project = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        project.user_id = 3;
        var createdProject = ProjectFixture.create(project,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        request.userId = 3;
        request.language_id = 1;
        request.project_id = createdProject.id;
        CodeFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Code.class);

        var response  = CodeFixture.getAll(token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", Code.class);
        assertThat(response).isNotEmpty();
    }

    @Test
    void getCodeById() {
        var token = TokenFixture.userToken();

        var request = CodeFixture.codeToCodeRequest(globalObject.validCode);
        var project = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        project.user_id = 3;
        var createdProject = ProjectFixture.create(project,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        request.userId = 3;
        request.language_id = 1;
        request.project_id = createdProject.id;
        var code = CodeFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Code.class);

        var codeGet = CodeFixture.getById(code.getId(),token)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", Code.class);
        assertThat(codeGet.getId()).isEqualTo(code.getId());
        assertThat(codeGet.getContent()).isEqualTo(code.getContent());
        assertThat(codeGet.getNameCode()).isEqualTo(code.getNameCode());
        CodeFixture.getById(0,token)
                .then()
                .statusCode(404);
    }

    @Test
    void updateCode() {
        var request = CodeFixture.codeToCodeRequest(globalObject.validCode);
        var token = TokenFixture.userToken();

        var project = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        project.user_id = 3;
        var createdProject = ProjectFixture.create(project,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        request.userId = 3;
        request.language_id = 1;
        request.project_id = createdProject.id;
        var code = CodeFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Code.class);

        request.content = "newContent";
        request.name = "newName";
        request.language_id = 2;

        var updated = CodeFixture.update(code.getId(),request,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".",Code.class);
        assertThat(updated.getNameCode()).isEqualTo(request.name);
        assertThat(updated.getContent()).isEqualTo(request.content);
        assertThat(updated.getLanguage().getId()).isEqualTo(request.language_id);


        CodeFixture.update(0,request,token).then()
                .statusCode(400);

        var r  = new CodeRequest(); // requête ok, mais rien n est modifié
        r.name = "";
        r.content = "";
        CodeFixture.update(updated.getId(), r,token).then()
                .statusCode(200);
    }

    @Test
    void deleteCode() {
        var request = CodeFixture.codeToCodeRequest(globalObject.validCode);
        var token = TokenFixture.userToken();

        var project = ProjectFixture.projectToProjectRequest(globalObject.validProject);
        project.user_id = 3;
        var createdProject = ProjectFixture.create(project,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", ProjectResponse.class);

        request.userId = 3;
        request.language_id = 1;
        request.project_id = createdProject.id;
        var code = CodeFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Code.class);

        CodeFixture.deleteById(code.getId(),token).then()
                .statusCode(204);
        CodeFixture.deleteById(code.getId(),token).then()
                .statusCode(404);
    }
}