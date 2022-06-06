package com.esgi.api_project_annuel.web.controller.fixture;

import com.esgi.api_project_annuel.web.request.FollowRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class FollowFixture {
    public static Response create(FollowRequest followRequest,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(followRequest)
                .header("Authorization","Bearer "+token.access_token)
                .post("/follow/create");
    }

    public static Response getById(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/follow/" + id);
    }
    public static Response getFollower(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/follow/followed/" + id);
    }
    public static Response getFollowed(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/follow/follower/" + id);
    }

    public static Response deleteById(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .delete("/follow/delete/" + id);
    }

    public static Response getAll(Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/follow/");
    }
}
