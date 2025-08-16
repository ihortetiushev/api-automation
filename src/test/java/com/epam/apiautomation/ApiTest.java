package com.epam.apiautomation;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTest {
    String endpoint = "https://jsonplaceholder.typicode.com/users";
    @Test
    public void verifyStatusCodeIs200() {
        given().
        when().
                get(endpoint).
        then().
                log().ifValidationFails().
                assertThat().
                statusCode(200);
    }

    @Test
    public void verifyResponseContentTypeHeader() {
        given().
        when().
                get(endpoint).
        then().
                log().ifValidationFails().
                assertThat().statusCode(200).
                header("Content-Type", notNullValue()).
                header("Content-Type", equalTo("application/json; charset=utf-8"));
    }

    @Test
    public void verifyResponseBodyHas10Users() {
        given().
        when().
                get(endpoint).
        then().
                log().ifValidationFails().
                assertThat().statusCode(200).
                body("size()", equalTo(10));
    }
}
