import dto.GorestDto;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GorestTest {

    private static String TOKEN;
    private static String BASE_URI;
    private static RequestSpecification requestSpec;

    @BeforeClass
    public void setup() throws IOException {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config2.properties");
        properties.load(fis);
        fis.close();

        BASE_URI = properties.getProperty("base.uri");

        TOKEN = System.getenv("TOKEN") != null ? System.getenv("TOKEN") : properties.getProperty("token");

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath("/public/v1")
                .addHeader("Authorization", "Bearer " + TOKEN)
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void verifyUserCreationRetrievalAndDeletion() {
        Response usersBeforeCreation = RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .get("/users");

        Assert.assertEquals(usersBeforeCreation.getStatusCode(), 200);

        int usersCountBeforeCreation = usersBeforeCreation
                .jsonPath()
                .getInt("meta.pagination.total");

        System.out.println("Users before creation: " + usersCountBeforeCreation);

        GorestDto userPayload = new GorestDto();

        userPayload.setName("Spartak");
        userPayload.setEmail("spartak" + System.currentTimeMillis() + "@gmail.com");
        userPayload.setGender("male");
        userPayload.setStatus("active");

        Response userCreationResult = RestAssured
                .given()
                .spec(requestSpec)
                .body(userPayload)
                .when()
                .post("/users");

        Assert.assertEquals(userCreationResult.getStatusCode(), 201);

        GorestDto savedUser = userCreationResult
                .jsonPath()
                .getObject("data", GorestDto.class);

        Assert.assertNotNull(savedUser);

        int savedUserId = savedUser.getId();

        Assert.assertTrue(savedUserId > 0);

        System.out.println("Created user ID: " + savedUserId);

        Response userDetailsResult = RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .get("/users/" + savedUserId);

        Assert.assertEquals(userDetailsResult.getStatusCode(), 200);

        System.out.println("User retrieved successfully.");

        Response usersAfterCreation = RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .get("/users");

        Assert.assertEquals(usersAfterCreation.getStatusCode(), 200);

        int usersCountAfterCreation = usersAfterCreation
                .jsonPath()
                .getInt("meta.pagination.total");

        Assert.assertEquals(usersCountAfterCreation, usersCountBeforeCreation + 1, "User count should increase by one");

        System.out.println("Users after creation: " + usersCountAfterCreation);

        Response userDeletionResult = RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .delete("/users/" + savedUserId);

        Assert.assertEquals(userDeletionResult.getStatusCode(), 204);

        System.out.println("User deleted successfully.");

        Response usersAfterDeletion = RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .get("/users");

        Assert.assertEquals(usersAfterDeletion.getStatusCode(), 200);

        int usersCountAfterDeletion = usersAfterDeletion
                .jsonPath()
                .getInt("meta.pagination.total");

        Assert.assertEquals(usersCountAfterDeletion, usersCountBeforeCreation, "User count should return to its initial value");

        System.out.println("Users after deletion: " + usersCountAfterDeletion);

        Response deletedUserLookupResult = RestAssured
                .given()
                .spec(requestSpec)
                .when()
                .get("/users/" + savedUserId);

        Assert.assertEquals(deletedUserLookupResult.getStatusCode(), 404, "Deleted user should return 404");

        System.out.println("User lifecycle test passed.");
    }
}
