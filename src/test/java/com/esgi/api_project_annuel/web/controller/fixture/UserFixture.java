package com.esgi.api_project_annuel.web.controller.fixture;

import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.web.request.UserRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Java6BDDSoftAssertionsProvider;

import static io.restassured.RestAssured.given;
public class UserFixture {

    public static Response create(UserRequest userRequest){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(userRequest)
                .post("/user/create");
    }

    public static Response getById(int userId){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/user/" + userId);
    }

    public static Response getByPseudo(String pseudo){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/user/pseudo/" + pseudo);
    }
    public static Response getByEmail(String email){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/user/email/" + email);
    }


    public static Response changeLastname(int userId, UserRequest userRequest){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(userRequest)
                .patch("/user/lastname/" + userId);
    }

    public static Response changeFirstname(int userId, UserRequest userRequest){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(userRequest)
                .patch("/user/firstname/" + userId);
    }
    public static Response changePassword(int userId, UserRequest userRequest){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(userRequest)
                .patch("/user/password/" + userId);
    }

    public static Response changeEmail(int userId, UserRequest userRequest){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(userRequest)
                .patch("/user/email/" + userId);

    }


    public static Response deleteById(int userId){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/user/" + userId);
    }

    public static Response getAll(){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/user/");
    }



    public static Response login(UserRequest userRequest){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(userRequest)
                .get("/user/login");
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

    public static Response changePseudo(int id, UserRequest request) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .patch("/user/pseudo/" + id);
    }
}
