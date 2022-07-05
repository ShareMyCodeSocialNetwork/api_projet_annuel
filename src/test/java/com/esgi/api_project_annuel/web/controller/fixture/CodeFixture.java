package com.esgi.api_project_annuel.web.controller.fixture;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.web.request.CodeRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CodeFixture {
    public static Response create(CodeRequest codeRequest, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(codeRequest)
                .header("Authorization","Bearer "+token.access_token)
                .post("/code/create");
    }

    public static Response getById(int id, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/code/" + id);
    }

    public static Response getByProject(int projectId, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/code/project/" + projectId);
    }
    public static Response getByUser(int userId, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/code/user/" + userId);
    }



    public static Response update(int id, CodeRequest codeRequest,Token token){

        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(codeRequest)
                .header("Authorization","Bearer "+token.access_token)
                .put("/code/update/" + id);
    }
    


    public static Response deleteById(int id, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .delete("/code/delete/" + id);
    }

    public static Response getAll( Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/code/");
    }





    public static CodeRequest codeToCodeRequest(Code code){
        var request = new CodeRequest();
        request.content = code.getContent();
        request.name = code.getNameCode();
        return request;
    }
}
