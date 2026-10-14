import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PetTest {

    @BeforeClass
    public void setup() {RestAssured.baseURI = "https://petstore.swagger.io/v2";}

    @Test
    public void petTest() {

        String createPetJson = """
                {
                  "id": 123456,
                  "name": "avcharka",
                  "status": "available"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(createPetJson)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .log().body()
                .body("name", equalTo("avcharka"))
                .body("id", equalTo(123456));

        given()
                .when()
                .get("/pet/" + 123456)
                .then()
                .statusCode(200)
                .log().body()
                .body("name", equalTo("avcharka"));

        given()
                .when()
                .delete("/pet/" + 123456)
                .then()
                .statusCode(200)
                .log().body()
                .body("message", equalTo(String.valueOf(123456)));

        given()
                .when()
                .get("/pet/" + 123456)
                .then()
                .statusCode(404)
                .log().body()
                .body("message", equalTo("Pet not found"));
    }
}