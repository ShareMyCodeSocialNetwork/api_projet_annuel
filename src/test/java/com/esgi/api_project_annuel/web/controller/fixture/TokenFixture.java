package com.esgi.api_project_annuel.web.controller.fixture;

import com.esgi.api_project_annuel.web.request.UserRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TokenFixture {
    public static Response login(UserRequest userRequest){
        return given()
                .when()
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("email", userRequest.email)
                .formParam("password",userRequest.password)
                .post("/login");
    }

    public static Response refresh(String token){
        return given()
                .when()
                .formParam("Authorization", "Bearer "+token)
                .post("/token/refresh/");
    }
}
