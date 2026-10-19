package controllers;

import dto.UserDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserController {
    private final RequestSpecification requestSpec;

    public UserController(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response createUser(UserDto user) {
        return RestAssured.given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/users");
    }

    public Response getUser(Long userId) {
        return RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users/" + userId);
    }

    public Response deleteUser(Long userId) {
        return RestAssured.given()
                .spec(requestSpec)
                .when()
                .delete("/users/" + userId);
    }
}
