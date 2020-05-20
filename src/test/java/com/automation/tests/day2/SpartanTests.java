package com.automation.tests.day2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SpartanTests {

    String Base_URL = "http://54.224.118.38:8000";

    //URI (Uniform Resource Identifier) = URL + URN = http://www.google.com/index.html
    //URL (Uniform Resource Locator) = http://www.google.com
    //URN (Uniform Resource Name)    = /index.html

    @Test
    @DisplayName("Get list of all spartans") //optional
    public void getAllSpartans() {
        //401-unauthorized, with no credentials request fails
        //how to provide credentials?
        //there are different types of authentication: basic, oauth 1.0, oauth 2.0, api key, bearer token, etc...
        //spartan app requires basic authentication: username and password

        given().
                auth().basic("admin", "admin").
                baseUri(Base_URL).
                when().
                get("/api/spartans").prettyPeek().
                then().statusCode(200);
    }
}
