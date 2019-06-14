package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

public class AddPostTest extends FunctionalTests {

    private static final String POST_API_CONFIRMED = "blog/user/1/post";

    @Test
    public void userWithStatusConfirmedShouldBeAbleToAddPost(){
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


}
