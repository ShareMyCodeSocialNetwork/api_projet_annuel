package com.esgi.api_project_annuel.web.controller.fixture;

import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.web.request.PostRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostFixture {
    public static Response create(PostRequest postRequest, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(postRequest)
                .header("Authorization","Bearer "+token.access_token)
                .post("/post/create");
    }

    public static Response getById(int id, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/post/" + id);
    }

    public static Response getByUser(int idUser, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/post/user/" + idUser);
    }


    public static Response changeContent(int id, PostRequest postRequest,Token token){

        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(postRequest)
                .header("Authorization","Bearer "+token.access_token)
                .patch("/post/update/" + id);
    }



    public static Response deleteById(int id, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .delete("/post/" + id);
    }

    public static Response getAll(Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/post/");
    }





    public static PostRequest postToPostRequest(Post post){
        var request = new PostRequest();
        request.content = post.getContent();
        return request;
    }
}
