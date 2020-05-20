package com.automation.tests.day2;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

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
                // authentication - who you are? you need to tell  to the server before getting any data
                        baseUri(Base_URL).
                when().
                get("/api/spartans").prettyPeek().
                then().statusCode(200);
        // 200 - always after successful GET request
    }

    // add new spartant

    @Test
    @DisplayName("Add new spartan")
    public void addSpartan() {
        // JSON supports different data types: string, integer, boolean
        String body = "{\"gender\": \"Male\", \"name\": \"Kirk Douglas\", \"phone\": 3012345678}";

        // instead of string variable, we can use external JSON file
        // use File class to read JSON and pass it into body
        // provide path to the JSON as a parameter
        File jsonFile = new File(System.getProperty("user.dir") + "/spartan.json");

        //to create new item, we perform POST request
        // contentType(ContentType.JSON) - to tell web service what kind of media type we send
        given().
                contentType(ContentType.JSON).
                auth().basic("admin", "admin").
                body(jsonFile).
                baseUri(Base_URL).
                when().
                post("/api/spartans").prettyPeek().
                then().statusCode(201);
        // 201 - always for POST request

    }

    @Test
    @DisplayName("Delete some spartan and verify status code is 356")
    public void deleteSpartanTest() {

        // {id} - path parameter
        // YOU CANNOT DELETE SOMETHING TWICE
        // we use delete() method to delete something
        // 4XX - always after unsuccessful request and it was your FAULT
        // ALL HTTP STATUS CODES HAVE SAME MEANING EVERYWHERE
        given().
                auth().basic("admin", "admin").
                baseUri(Base_URL).
                when().
                delete("/api/spartans/{id}", 356).prettyPeek().
                then().
                statusCode(204);
        // 204 - No content, most common status code for successful DELETE request
    }
}
