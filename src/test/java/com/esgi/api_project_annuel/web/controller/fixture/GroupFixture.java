package com.esgi.api_project_annuel.web.controller.fixture;

import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.web.request.GroupRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Java6BDDSoftAssertionsProvider;

import static io.restassured.RestAssured.given;

public class GroupFixture {

    public static Response create(GroupRequest groupRequest, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(groupRequest)
                .header("Authorization","Bearer "+token.access_token)
                .post("/group/create");
    }

    public static Response getById(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/group/" + id);
    }



    public static Response update(int id, GroupRequest groupRequest,Token token){

        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(groupRequest)
                .header("Authorization","Bearer "+token.access_token)
                .put("/group/update/" + id);
    }



    public static Response deleteById(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .delete("/group/delete/" + id);
    }

    public static Response getAll(Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/group/");
    }

    public static Response changeName(int id, GroupRequest request,Token token) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .header("Authorization","Bearer "+token.access_token)
                .patch("/group/update/name/" + id);
    }
    public static Response changeDescription(int id, GroupRequest request,Token token) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .header("Authorization","Bearer "+token.access_token)
                .patch("/group/update/description/" + id);
    }

    public static Response getByName(String groupName, Token token) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/group/name/" + groupName);

    }

    public static GroupRequest groupToGroupRequest(Group group){
        var request = new GroupRequest();
        request.name = group.getName();
        request.description = group.getDescription();
        return request;
    }




}
