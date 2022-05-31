package com.esgi.api_project_annuel.web.controller.fixture;

import com.esgi.api_project_annuel.Domain.entities.Language;
import com.esgi.api_project_annuel.web.request.LanguageRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LanguageFixture {
    public static Response create(LanguageRequest languageRequest){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(languageRequest)
                .post("/language/create");
    }

    public static Response getById(int id){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/language/" + id);
    }


    public static Response changeName(int id, LanguageRequest languageRequest){

        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(languageRequest)
                .put("/language/update/" + id);
    }



    public static Response deleteById(int id){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/language/delete/" + id);
    }

    public static Response getAll(){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/language/");
    }





    public static LanguageRequest languageToLanguageRequest(Language language){
        var request = new LanguageRequest();
        request.name = language.getName();
        return request;
    }
}
