package com.epam.apiautomation;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTest {
    final static String ENDPOINT = "https://jsonplaceholder.typicode.com/users";

    @Test
    public void verifyStatusCodeIs200() {
        given().
                when().
                get(ENDPOINT).
                then().
                log().ifValidationFails().
                assertThat().
                statusCode(200);
    }

    @Test
    public void verifyResponseContentTypeHeader() {
        given().
                when().
                get(ENDPOINT).
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
                get(ENDPOINT).
                then().
                log().ifValidationFails().
                assertThat().statusCode(200).
                body("size()", equalTo(10));
    }

    @Test
    public void createUserTest() {
        String body = """
                {
                  "id": 11,
                  "name": "New Test User",
                  "username": "Test.User",
                  "email": "test.user@example.com",
                  "address": {
                    "street": "Main Street",
                    "suite": "Suite 100",
                    "city": "Testville",
                    "zipcode": "12345",
                    "geo": {
                      "lat": "40.1234",
                      "lng": "-74.5678"
                    }
                  },
                  "phone": "123-456-7890",
                  "website": "testsite.com",
                  "company": {
                    "name": "Test Company",
                    "catchPhrase": "Testing everything",
                    "bs": "deliver test-driven results"
                  }
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post(ENDPOINT)
        .then()
                .log().ifValidationFails()
                .statusCode(201)

                .body("id", equalTo(11))
                .body("name", equalTo("New Test User"))
                .body("username", equalTo("Test.User"))
                .body("email", equalTo("test.user@example.com"));
    }
    @Test
    public void getUserTest() {
        given()
        .when()
                .get(ENDPOINT + "/1")
        .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Leanne Graham"))
                .body("username", equalTo("Bret"))
                .body("email", equalTo("Sincere@april.biz"));
    }

    @Test
    public void updateUserTest() {
        String body = """
                {
                  "id": 10,
                  "name": "Updated Test User",
                  "username": "Test.UpdateUser",
                  "email": "test.updateUser@example.com",
                  "address": {
                            "street": "Kattie Turnpike",
                            "suite": "Suite 198",
                            "city": "Lebsackbury",
                            "zipcode": "31428-2261",
                            "geo": {
                              "lat": "-38.2386",
                              "lng": "57.2232"
                            }
                          },
                          "phone": "024-648-3804",
                          "website": "ambrose.net",
                          "company": {
                            "name": "Hoeger LLC",
                            "catchPhrase": "Centralized empowering task-force",
                            "bs": "target end-to-end models"
                          }
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .put(ENDPOINT+"/10")
        .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("id", equalTo(10))
                .body("name", equalTo("Updated Test User"))
                .body("username", equalTo("Test.UpdateUser"))
                .body("email", equalTo("test.updateUser@example.com"));
    }

    @Test
    public void deleteUserTest() {
        given()
        .when()
                .delete(ENDPOINT + "/3")
        .then()
                .log().ifValidationFails()
                .statusCode(200);
    }

}
