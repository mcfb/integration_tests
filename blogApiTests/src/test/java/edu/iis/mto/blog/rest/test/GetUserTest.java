package edu.iis.mto.blog.rest.test;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

public class GetUserTest {

    private static final String USER_API = "/blog/user/";
    public static final String POST_SEARCH_USER_REMOVED = "/blog/user/4/post";

    @Test
    public void notExistingUserShouldReturnHTTPStatus404(){

        String id = "123456";

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .when()
                .get(USER_API + id);

    }

    @Test
    public void findUserShouldNotReturnRemovedUser() {
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


}
