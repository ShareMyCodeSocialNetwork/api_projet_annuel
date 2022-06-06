package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Language;
import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.LanguageFixture;
import com.esgi.api_project_annuel.web.controller.fixture.TokenFixture;
import com.esgi.api_project_annuel.web.response.RoleResponse;
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
class LanguageControllerTest {
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
        var request = LanguageFixture.languageToLanguageRequest(globalObject.validLanguage);
        var token = TokenFixture.adminToken();
        LanguageFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Language.class);
        request.name = "";
        LanguageFixture.create(request, token).then()
                .statusCode(400);
    }

    @Test
    void getAllProgrammingLanguages() {
        var request = LanguageFixture.languageToLanguageRequest(globalObject.validLanguage);
        var token = TokenFixture.adminToken();
        LanguageFixture.create(request, token).then()
                .statusCode(201);
        var response  = LanguageFixture.getAll(token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", Language.class);
        assertThat(response).isNotEmpty();
    }

    @Test
    void getLanguageById() {
        var request = LanguageFixture.languageToLanguageRequest(globalObject.validLanguage);
        var token = TokenFixture.adminToken();
        var response = LanguageFixture.create(request, token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Language.class);

        LanguageFixture.getById(response.getId(), token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", Language.class);

        LanguageFixture.getById(0, token).then()
                .statusCode(404);
    }

    @Test
    void updateLanguage() {
        var request = LanguageFixture.languageToLanguageRequest(globalObject.validLanguage);
        var token = TokenFixture.adminToken();
        var response = LanguageFixture.create(request, token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", RoleResponse.class);
        request.name = "NewName";
        response = LanguageFixture.changeName(response.id,request, token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", RoleResponse.class);
        assertThat(response.name).isEqualTo(request.name);

        request.name = "";
        LanguageFixture.changeName(response.id,request, token).then()
                .statusCode(400);

    }

    @Test
    void deleteLanguage() {
        var request = LanguageFixture.languageToLanguageRequest(globalObject.validLanguage);
        var token = TokenFixture.adminToken();
        var response = LanguageFixture.create(request, token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Language.class);

        LanguageFixture.deleteById(response.getId(), token).then()
                .statusCode(204);

        LanguageFixture.deleteById(response.getId(), token).then()
                .statusCode(404);
    }
}