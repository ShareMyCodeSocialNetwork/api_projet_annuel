package com.esgi.api_project_annuel.web.controller.fixture;

import com.esgi.api_project_annuel.web.request.UserRoleGroupRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserRoleGroupFixture {
    public static Response create(UserRoleGroupRequest userRoleGroupRequest, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(userRoleGroupRequest)
                .header("Authorization","Bearer "+token.access_token)
                .post("/user_role_group/create");
    }

    public static Response getAll(Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/user_role_group/");
    }

    public static Response getById(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/user_role_group/" + id);
    }
    public static Response changeUserRoleInGroup(int userRoleGroupId, UserRoleGroupRequest request,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .header("Authorization","Bearer "+token.access_token)
                .patch("/user_role_group/" + userRoleGroupId + "/change/user/role");
    }


    public static Response getByGroup(int groupId, Token token) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/user_role_group/group/" + groupId);
    }

    public static Response getByUser(int userId, Token token) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/user_role_group/user/" + userId);
    }

    public static Response getByGroupAndUser(int groupId, int userId, Token token) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/user_role_group/group/"+groupId+"/user/" + userId);
    }

    public static Response deleteById(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .delete("/user_role_group/delete/" + id);
    }

}
