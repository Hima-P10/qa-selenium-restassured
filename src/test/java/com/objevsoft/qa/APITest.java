package com.objevsoft.qa;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class APITest {

    @Test
    public void testApiConnectivity() {
        String url = "https://www.google.com/search?q=test";
        Response response = given().get(url);
        Assert.assertEquals(response.statusCode(), 200);
    }
}
