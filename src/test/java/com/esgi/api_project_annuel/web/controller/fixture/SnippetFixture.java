package com.esgi.api_project_annuel.web.controller.fixture;

import com.esgi.api_project_annuel.Domain.entities.Snippet;
import com.esgi.api_project_annuel.web.request.SnippetRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class SnippetFixture {
    public static Response create(SnippetRequest snippetRequest, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(snippetRequest)
                .header("Authorization","Bearer "+token.access_token)
                .post("/snippet/create");
    }

    public static Response getById(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/snippet/" + id);
    }

    public static Response getByUser(int userId,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/snippet/user/" + userId);
    }


    public static Response update(int id, SnippetRequest snippetRequest,Token token){

        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(snippetRequest)
                .header("Authorization","Bearer "+token.access_token)
                .put("/snippet/update/" + id);
    }



    public static Response deleteById(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .delete("/snippet/delete/" + id);
    }

    public static Response getAll(Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/snippet/");
    }

    public static SnippetRequest snippetToSnippetRequest(Snippet snippet){
        var request = new SnippetRequest();
        request.name = snippet.getName();
        request.content = snippet.getContent();
        return request;
    }
}
