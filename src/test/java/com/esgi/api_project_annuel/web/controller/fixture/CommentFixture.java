package com.esgi.api_project_annuel.web.controller.fixture;

import com.esgi.api_project_annuel.Domain.entities.Comment;
import com.esgi.api_project_annuel.web.request.CommentRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CommentFixture {
    public static Response create(CommentRequest commentRequest, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(commentRequest)
                .header("Authorization","Bearer "+token.access_token)
                .post("/comment/create");
    }

    public static Response getById(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/comment/" + id);
    }

    public static Response update(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .patch("/comment/update/" + id);
    }


    public static Response deleteById(int id,Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .delete("/comment/delete/" + id);
    }

    public static Response getAll(Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/comment/");
    }


    public static Response getByPost(int postId, Token token) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/comment/post/" + postId);

    }

    public static CommentRequest commentToCommentRequest(Comment comment){
        var request = new CommentRequest();
        request.content = comment.getContent();
        return request;
    }
}
