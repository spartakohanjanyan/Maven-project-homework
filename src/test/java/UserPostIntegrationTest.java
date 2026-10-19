import controllers.PostController;
import controllers.UserController;
import dto.PostDto;
import dto.UserDto;

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
import java.util.List;
import java.util.Properties;

public class UserPostIntegrationTest {

    private RequestSpecification requestSpec;
    private UserController userController;
    private PostController postController;

    private String token;

    private final String uniqueNumber = String.valueOf(System.currentTimeMillis());

    @BeforeClass
    public void setup() throws IOException {

        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream("src/test/resources/config2.properties")) {
            properties.load(fis);
        }

        token = System.getenv("TOKEN");

        if (token == null) {
            token = properties.getProperty("token");
        }

        Assert.assertNotNull(token, "Token is missing");
        Assert.assertFalse(token.isBlank(), "Token is empty");

        String baseUri = properties.getProperty("base.uri");

        RestAssured.baseURI = baseUri;

        requestSpec = new RequestSpecBuilder()
                .setBasePath("/public/v1")
                .addHeader("Authorization", "Bearer " + token)
                .setContentType(ContentType.JSON)
                .build();

        userController = new UserController(requestSpec);
        postController = new PostController(requestSpec);
    }

    private UserDto createUserBody(
            String name, String email,
            String gender, String status) {

        return new UserDto(name, email, gender, status);
    }

    private PostDto createPostBody(
            String title, String body, Long userId) {
        return new PostDto(title, body, userId);
    }

    @Test
    public void testUserPostLifecycle() {
        UserDto user1Body = createUserBody(
                "Spartak User 1",
                "spartak1_" + uniqueNumber + "@gmail.com",
                "male",
                "active"
        );

        Response user1Response =
                userController.createUser(user1Body);

        Assert.assertEquals(
                user1Response.getStatusCode(),
                201,
                user1Response.asPrettyString()
        );

        UserDto createdUser1 = user1Response.jsonPath()
                        .getObject("data", UserDto.class);

        Long userId1 = createdUser1.getId();

        Assert.assertNotNull(userId1);

        PostDto post1Body = createPostBody(
                "User 1 Post 1",
                "First user's first post", userId1
        );

        PostDto post2Body = createPostBody(
                "User 1 Post 2",
                "First user's second post", userId1
        );

        Response post1Response = postController.createPost(post1Body);

        Assert.assertEquals(
                post1Response.getStatusCode(),
                201,
                post1Response.asPrettyString()
        );

        Response post2Response = postController.createPost(post2Body);

        Assert.assertEquals(
                post2Response.getStatusCode(),
                201,
                post2Response.asPrettyString()
        );

        PostDto createdPost1 = post1Response.jsonPath()
                        .getObject("data", PostDto.class);

        PostDto createdPost2 = post2Response.jsonPath()
                        .getObject("data", PostDto.class);

        Long postId1 = createdPost1.getId();
        Long postId2 = createdPost2.getId();

        Assert.assertNotNull(postId1);
        Assert.assertNotNull(postId2);

        Response user1PostsResponse = postController.getPostsByUser(userId1);

        Assert.assertEquals(
                user1PostsResponse.getStatusCode(),
                200,
                user1PostsResponse.asPrettyString()
        );

        List<PostDto> user1Posts = user1PostsResponse.jsonPath()
                        .getList("data", PostDto.class);

        Assert.assertEquals(user1Posts.size(), 2);

        Assert.assertTrue(
                user1Posts.stream().anyMatch(
                        post -> "First user's first post"
                                .equals(post.getBody())),
                "First post body was not found"
        );

        Assert.assertTrue(
                user1Posts.stream().anyMatch(
                        post -> "First user's second post"
                                .equals(post.getBody())),
                "Second post body was not found"
        );

        UserDto user2Body = createUserBody(
                "Spartak User 2",
                "spartak2_" + uniqueNumber + "@gmail.com",
                "male",
                "active"
        );

        Response user2Response = userController.createUser(user2Body);

        Assert.assertEquals(
                user2Response.getStatusCode(),
                201,
                user2Response.asPrettyString()
        );

        UserDto createdUser2 = user2Response.jsonPath()
                        .getObject("data", UserDto.class);

        Long userId2 = createdUser2.getId();

        Assert.assertNotNull(userId2);

        PostDto user2Post1Body = createPostBody(
                "User 2 Post 1",
                "Second user's first post", userId2
        );

        PostDto user2Post2Body = createPostBody(
                "User 2 Post 2",
                "Second user's second post", userId2
        );

        Response user2Post1Response = postController.createPost(user2Post1Body);

        Assert.assertEquals(
                user2Post1Response.getStatusCode(),
                201,
                user2Post1Response.asPrettyString()
        );

        Response user2Post2Response = postController.createPost(user2Post2Body);

        Assert.assertEquals(
                user2Post2Response.getStatusCode(),
                201,
                user2Post2Response.asPrettyString()
        );

        PostDto createdUser2Post1 = user2Post1Response.jsonPath()
                        .getObject("data", PostDto.class);

        PostDto createdUser2Post2 = user2Post2Response.jsonPath()
                        .getObject("data", PostDto.class);

        Long user2PostId1 = createdUser2Post1.getId();
        Long user2PostId2 = createdUser2Post2.getId();

        Assert.assertNotNull(user2PostId1);
        Assert.assertNotNull(user2PostId2);

        Response postsBeforeDeleteResponse = postController.getPostsByUser(userId2);

        Assert.assertEquals(
                postsBeforeDeleteResponse.getStatusCode(),
                200,
                postsBeforeDeleteResponse.asPrettyString()
        );

        List<PostDto> postsBeforeDelete = postsBeforeDeleteResponse.jsonPath()
                        .getList("data", PostDto.class);

        Assert.assertEquals(postsBeforeDelete.size(), 2);

        Response deletePostResponse = postController.deletePost(user2PostId1);

        Assert.assertEquals(
                deletePostResponse.getStatusCode(),
                204,
                deletePostResponse.asPrettyString()
        );

        Response postsAfterDeleteResponse = postController.getPostsByUser(userId2);

        Assert.assertEquals(
                postsAfterDeleteResponse.getStatusCode(),
                200,
                postsAfterDeleteResponse.asPrettyString()
        );

        List<PostDto> postsAfterDelete = postsAfterDeleteResponse.jsonPath()
                        .getList("data", PostDto.class);

        Assert.assertEquals(
                postsAfterDelete.size(),
                postsBeforeDelete.size() - 1,
                "Post count did not decrease by one"
        );

        Assert.assertEquals(postsAfterDelete.size(), 1);

        Assert.assertEquals(
                postsAfterDelete.get(0).getId(),
                user2PostId2,
                "Unexpected post remained after deletion"
        );

        Response deleteUser1Response = userController.deleteUser(userId1);

        Assert.assertEquals(
                deleteUser1Response.getStatusCode(),
                204,
                deleteUser1Response.asPrettyString()
        );

        Response response = postController.getPostsByUser(userId1);

        Assert.assertEquals(response.getStatusCode(), 200);

        List<PostDto> posts = response.jsonPath()
                .getList("data", PostDto.class);

        Assert.assertTrue(
                posts.isEmpty(), "Deleted user's posts should not exist"
        );

        Response deleteUser2Response = userController.deleteUser(userId2);

        Assert.assertEquals(
                deleteUser2Response.getStatusCode(),
                204,
                deleteUser2Response.asPrettyString()
        );

        Response deletedPostResponse = postController.getPost(user2PostId2);

        Assert.assertEquals(
                deletedPostResponse.getStatusCode(),
                404,
                deletedPostResponse.asPrettyString()
        );
    }
}
