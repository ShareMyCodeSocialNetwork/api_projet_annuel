package com.esgi.api_project_annuel.web.controller.fixture;

import com.esgi.api_project_annuel.Domain.entities.Project;
import com.esgi.api_project_annuel.web.request.ProjectRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProjectFixture {
    public static Response create(ProjectRequest projectRequest, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(projectRequest)
                .header("Authorization","Bearer "+token.access_token)
                .post("/project/create");
    }

    public static Response getAll(Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/project/");
    }

    public static Response getById(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/project/" + id);
    }

    public static Response getByIdFull(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/project/full/" + id);
    }

    public static Response getByName(String projectName, Token token) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/project/name/" + projectName);
    }

    public static Response getByGroup(int groupId, Token token) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/project/group/" + groupId);
    }

    public static Response getByUser(int userId, Token token) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/project/user/" + userId);
    }

    public static Response changeGroup(int id, ProjectRequest projectRequest,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(projectRequest)
                .header("Authorization","Bearer "+token.access_token)
                .patch("/project/group/" + id);
    }

    public static Response changeOwner(int id, ProjectRequest projectRequest,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(projectRequest)
                .header("Authorization","Bearer "+token.access_token)
                .patch("/project/owner/" + id);
    }

    public static Response changeName(int id, ProjectRequest request,Token token) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .header("Authorization","Bearer "+token.access_token)
                .patch("/project/" + id + "/name");
    }

    public static Response changeDescription(int id, ProjectRequest request,Token token) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .header("Authorization","Bearer "+token.access_token)
                .patch("/project/" + id + "/description");
    }

    public static Response deleteById(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .delete("/project/delete/" + id);
    }







    public static ProjectRequest projectToProjectRequest(Project project){
        var request = new ProjectRequest();
        request.name = project.getName();
        request.description = project.getDescription();
        return request;
    }

}
