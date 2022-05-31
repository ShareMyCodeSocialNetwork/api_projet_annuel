package com.esgi.api_project_annuel.web.controller.fixture;

import com.esgi.api_project_annuel.Domain.entities.Role;
import com.esgi.api_project_annuel.web.request.RoleRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RoleFixture {
    public static Response create(RoleRequest roleRequest){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(roleRequest)
                .post("/role/create");
    }

    public static Response getById(int id){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/role/" + id);
    }

    public static Response getByName(String roleName){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/role/name/" + roleName);
    }


    public static Response changeName(int id, RoleRequest roleRequest){

        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(roleRequest)
                .patch("/role/" + id);
    }



    public static Response deleteById(int id){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/role/" + id);
    }

    public static Response getAll(){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/role/");
    }





    public static RoleRequest roleToRoleRequest(Role role){
        var request = new RoleRequest();
        request.name = role.getTitlePermission();
        return request;
    }
}
