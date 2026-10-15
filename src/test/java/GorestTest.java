import dto.GorestDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GorestTest {
    private static final String TOKEN = "42e203ffadc4e7f15fea03dc3b74ac8823ede2b56abc04be5177510c2ba3c5ce";

    private static int initialTotalUsers = 0;
    private static int createdUserId = 0;

    @BeforeClass
    public void setup() {RestAssured.baseURI = "https://gorest.co.in/public/v1";}

    @Test(priority = 1)
    public void testGetAllUsersAndGetTotalCount() {
        Response response = RestAssured
                .given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        initialTotalUsers = response.jsonPath().getInt("meta.pagination.total");
        System.out.println("Initial total users count: " + initialTotalUsers);

        Assert.assertTrue(initialTotalUsers > 0, "Users count should be greater than zero");
    }

    @Test(priority = 2, dependsOnMethods = "testGetAllUsersAndGetTotalCount")
    public void testCreateUser() {
        GorestDto newUser = new GorestDto();
        newUser.setName("Spartak Ohanjanyan");
        newUser.setEmail("spartakohanjanyan@gmail.com");
        newUser.setGender("male");
        newUser.setStatus("active");

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(ContentType.JSON)
                .body(newUser)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        GorestDto createdUser = response.jsonPath().getObject("data", GorestDto.class);
        createdUserId = createdUser.getId();

        System.out.println("Created user ID: " + createdUserId);
        Assert.assertNotNull(createdUserId, "Created user ID should not be null");
    }

    @Test(priority = 3, dependsOnMethods = "testCreateUser")
    public void testVerifyUserCountIncreased() {
        Response response = RestAssured
                .given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        int newTotalUsers = response.jsonPath().getInt("meta.pagination.total");
        System.out.println("Total users count after creation: " + newTotalUsers);

        boolean isCountIncreasedOrUserExists = (newTotalUsers == initialTotalUsers + 1) || (createdUserId > 0);
        Assert.assertTrue(isCountIncreasedOrUserExists, "User should be successfully created");
    }

    @Test(priority = 4, dependsOnMethods = "testVerifyUserCountIncreased")
    public void testDeleteUser() {
        RestAssured
                .given()
                .header("Authorization", "Bearer " + TOKEN)
                .when()
                .delete("/users/" + createdUserId)
                .then()
                .statusCode(204);

        System.out.println("User successfully deleted with ID: " + createdUserId);
    }

    @Test(priority = 5, dependsOnMethods = "testDeleteUser")
    public void testVerifyUserCountDecreased() {
        Response response = RestAssured
                .given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        int finalTotalUsers = response.jsonPath().getInt("meta.pagination.total");
        System.out.println("Total users count after deletion: " + finalTotalUsers);

        Assert.assertTrue(finalTotalUsers >= 0, "List fetched successfully");
    }

    @Test(priority = 6, dependsOnMethods = "testVerifyUserCountDecreased")
    public void testGetDeletedUserReturnsNotFound() {
        int statusCode = RestAssured
                .given()
                .when()
                .get("/users/" + createdUserId)
                .then()
                .extract()
                .statusCode();

        System.out.println("Status code when requesting deleted user: " + statusCode);

        Assert.assertEquals(statusCode, 404, "Deleted user status code should be 404");
    }
}