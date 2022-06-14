package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Snippet;
import com.esgi.api_project_annuel.GlobalObject;
import com.esgi.api_project_annuel.web.controller.fixture.SnippetFixture;
import com.esgi.api_project_annuel.web.controller.fixture.TokenFixture;
import com.esgi.api_project_annuel.web.request.SnippetRequest;
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
class SnippetControllerTest {
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
        var token = TokenFixture.userToken();
        var request = SnippetFixture.snippetToSnippetRequest(globalObject.validSnippet);
        request.language_id = 1;
        request.user_id = 3;

        SnippetFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Snippet.class);


        SnippetFixture.create(new SnippetRequest(),token).then()
                .statusCode(406);
    }

    @Test
    void getAllSnippets() {
        var token = TokenFixture.userToken();
        var request = SnippetFixture.snippetToSnippetRequest(globalObject.validSnippet);
        request.language_id = 1;
        request.user_id = 3;

        SnippetFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Snippet.class);

        var snippets = SnippetFixture.getAll(token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".",Snippet.class);
        assertThat(snippets).isNotEmpty();
    }
    @Test
    void getAllSnippetsByUser() {
        var token = TokenFixture.userToken();
        var request = SnippetFixture.snippetToSnippetRequest(globalObject.validSnippet);
        request.language_id = 1;
        request.user_id = 3;

        SnippetFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Snippet.class);

        var snippets = SnippetFixture.getByUser(3,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getList(".",Snippet.class);
        assertThat(snippets).isNotEmpty();
    }

    @Test
    void getSnippetById() {
        var token = TokenFixture.userToken();
        var request = SnippetFixture.snippetToSnippetRequest(globalObject.validSnippet);
        request.language_id = 1;
        request.user_id = 3;

        var snippetCreated = SnippetFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Snippet.class);

        var snippet = SnippetFixture.getById(snippetCreated.getId(),token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".",Snippet.class);

        assertThat(snippet.getContent()).isEqualTo(snippetCreated.getContent());
        assertThat(snippet.getName()).isEqualTo(snippetCreated.getName());
        assertThat(snippet.getId()).isEqualTo(snippetCreated.getId());
        assertThat(snippet.getUser().getId()).isEqualTo(snippetCreated.getUser().getId());
        assertThat(snippet.getLanguage().getId()).isEqualTo(snippetCreated.getLanguage().getId());

        SnippetFixture.getById(0,token).then()
                .statusCode(404);
    }

    @Test
    void updateSnippet() {
        var token = TokenFixture.userToken();
        var request = SnippetFixture.snippetToSnippetRequest(globalObject.validSnippet);
        request.language_id = 1;
        request.user_id = 3;

        var snippetCreated = SnippetFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Snippet.class);
        request.name = "1";
        request.language_id = 2;
        request.content = "new";

        var snippet = SnippetFixture.update(snippetCreated.getId(),request,token).then()
                .statusCode(200)
                .extract().body().jsonPath().getObject(".",Snippet.class);

        assertThat(snippet.getContent()).isEqualTo(request.content);
        assertThat(snippet.getName()).isEqualTo(request.name);
        assertThat(snippet.getUser().getId()).isEqualTo(request.user_id);
        assertThat(snippet.getLanguage().getId()).isEqualTo(request.language_id);

        SnippetFixture.update(0,request,token).then()
                .statusCode(400);

        var r = new SnippetRequest();
        r.content = "";
        r.name = "";
        SnippetFixture.update(snippetCreated.getId(), r,token).then()
                .statusCode(200);
    }

    @Test
    void deleteSnippet() {
        var token = TokenFixture.userToken();
        var request = SnippetFixture.snippetToSnippetRequest(globalObject.validSnippet);
        request.language_id = 1;
        request.user_id = 3;

        var snippetCreated = SnippetFixture.create(request,token).then()
                .statusCode(201)
                .extract().body().jsonPath().getObject(".", Snippet.class);

        SnippetFixture.deleteById(snippetCreated.getId(),token).then()
                .statusCode(204);
        SnippetFixture.deleteById(0,token).then()
                .statusCode(404);
    }
}