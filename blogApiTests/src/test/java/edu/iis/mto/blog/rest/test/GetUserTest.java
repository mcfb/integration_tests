package edu.iis.mto.blog.rest.test;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;

public class GetUserTest {

    private static final String USER_API = "/blog/user/";

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

}
