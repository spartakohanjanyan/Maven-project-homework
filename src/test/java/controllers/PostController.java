package controllers;

import dto.PostDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostController {

    private final RequestSpecification requestSpec;

    public PostController(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response createPost(PostDto post) {
        return RestAssured.given()
                .spec(requestSpec)
                .body(post)
                .when()
                .post("/posts");
    }

    public Response getPost(Long postId) {
        return RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/posts/" + postId);
    }

    public Response getPostsByUser(Long userId) {
        return RestAssured.given()
                .spec(requestSpec)
                .queryParam("user_id", userId)
                .when()
                .get("/posts");
    }

    public Response deletePost(Long postId) {
        return RestAssured.given()
                .spec(requestSpec)
                .when()
                .delete("/posts/" + postId);
    }
}
