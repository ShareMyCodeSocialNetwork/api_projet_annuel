package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.web.request.GroupRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Java6BDDSoftAssertionsProvider;

import static io.restassured.RestAssured.given;

public class GroupFixture {
    public static Response create(GroupRequest groupRequest){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(groupRequest)
                .post("/group/create");
    }

    public static Response getById(int id){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/group/" + id);
    }



    public static Response update(int id, GroupRequest groupRequest){

        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(groupRequest)
                .put("/group/update/" + id);
    }



    public static Response deleteById(int id){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/group/" + id);
    }

    public static Response getAll(){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/group/");
    }





    static GroupRequest groupToGroupRequest(Group group){
        var request = new GroupRequest();
        request.name = group.getName();
        return request;
    }

    public static Response getByName(String groupName) {
            return given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get("/group/name/" + groupName);

    }

    public static Response changeName(int id, GroupRequest request) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .patch("/group/change_name/" + id);
    }
}
