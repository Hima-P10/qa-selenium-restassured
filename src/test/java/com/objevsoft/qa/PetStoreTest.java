package com.objevsoft.qa;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class PetStoreTest {

    @Test
    public void testGetPet() {
        Response response = given()
            .baseUri("https://petstore.swagger.io/v2")
            .when()
            .get("/pet/findByStatus?status=available");

        Assert.assertEquals(response.statusCode(), 200);
    }
}

