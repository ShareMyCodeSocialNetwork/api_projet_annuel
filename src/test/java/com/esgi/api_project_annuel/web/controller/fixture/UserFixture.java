package com.esgi.api_project_annuel.web.controller.fixture;

import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.web.request.UserRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
public class UserFixture {

    public static Response create(UserRequest userRequest){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(userRequest)
                .post("/user/create");
    }

    public static Response getById(int userId, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/user/" + userId);
    }

    public static Response getByPseudo(String pseudo, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/user/pseudo/" + pseudo);
    }
    public static Response getByEmail(String email, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/user/email/" + email);
    }


    public static Response changeLastname(int userId, UserRequest userRequest, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(userRequest)
                .header("Authorization","Bearer "+token.access_token)
                .patch("/user/update/lastname/" + userId);
    }

    public static Response changeFirstname(int userId, UserRequest userRequest, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(userRequest)
                .header("Authorization","Bearer "+token.access_token)
                .patch("/user/update/firstname/" + userId);
    }
    public static Response changePassword(int userId, UserRequest userRequest, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(userRequest)
                .header("Authorization","Bearer "+token.access_token)
                .patch("/user/update/password/" + userId);
    }

    public static Response changeEmail(int userId, UserRequest userRequest, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(userRequest)
                .header("Authorization","Bearer "+token.access_token)
                .patch("/user/update/email/" + userId);

    }


    public static Response deleteById(int userId, Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer " + token.access_token)
                .delete("/user/delete/" + userId);
    }

    public static Response getAll(Token token){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .get("/user/");
    }



    /*public static Response login(UserRequest userRequest){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(userRequest)
                .get("/user/login");
    }*/

    public static Response changePseudo(int id, UserRequest request,Token token) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization","Bearer "+token.access_token)
                .body(request)
                .patch("/user/update/pseudo/" + id);
    }
    public static UserRequest userToUserRequest(User user){
        var request = new UserRequest();
        request.password = user.getPassword();
        request.email = user.getEmail();
        request.pseudo = user.getPseudo();
        request.firstname = user.getFirstname();
        request.lastname = user.getLastname();
        request.profilePicture = user.getProfilePicture();
        return request;
    }


}
