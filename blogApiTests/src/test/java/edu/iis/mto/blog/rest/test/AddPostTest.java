package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

public class AddPostTest extends FunctionalTests {

    private static final String POST_API_CONFIRMED = "blog/user/1/post";
    private static final String POST_LIKE_CONFIRMED = "/blog/user/1/like/2";
    private static final String POST_LIKE_AUTHOR = "/blog/user/1/like/1";
    public static final String POST_SEARCH_USER_REMOVED = "/blog/user/4/post";
    public static final String POST_SEARCH_VALID = "/blog/user/1/post";

    @Test
    public void userWithStatusConfirmedShouldBeAbleToAddPost() {
        JSONObject jsonObj = new JSONObject().put("entry", "test");
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_CREATED)
                .when()
                .post(POST_API_CONFIRMED);
    }


    @Test
    public void authorCannotLikeOwnPosts() {

        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(POST_LIKE_AUTHOR);

    }

    @Test
    public void userWithConfirmedStatusCanLikePost() {

        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(POST_LIKE_CONFIRMED);

    }

    @Test
    public void CannotLikePostMoreThanOnce() {

        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(POST_LIKE_AUTHOR);
    }


    @Test
    public void shouldReturnErrorIfSearchingForDeletedTests(){

        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .get(POST_SEARCH_USER_REMOVED);
    }


    @Test
    public void searchingForPostsShouldReturnProperAmountOfLikes(){


        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("likesCount", CoreMatchers.hasItem(0))
                .when()
                .get(POST_SEARCH_VALID);

    }


}
