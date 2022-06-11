package com.esgi.api_project_annuel.web.controller.fixture;

import com.esgi.api_project_annuel.Domain.entities.Role;
import com.esgi.api_project_annuel.web.request.RoleRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RoleFixture {
    public static Response create(RoleRequest roleRequest,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(roleRequest)
                .header("Authorization","Bearer "+token.access_token)
                .post("/role/create");
    }

    public static Response getById(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/role/" + id);
    }

    public static Response getByName(String roleName,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/role/name/" + roleName);
    }


    public static Response changeName(int id, RoleRequest roleRequest,Token token){

        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(roleRequest)
                .header("Authorization","Bearer "+token.access_token)
                .patch("/role/" + id);
    }



    public static Response deleteById(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .delete("/role/" + id);
    }

    public static Response getAll(Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/role/");
    }





    public static RoleRequest roleToRoleRequest(Role role){
        var request = new RoleRequest();
        request.name = role.getTitlePermission();
        return request;
    }
}
