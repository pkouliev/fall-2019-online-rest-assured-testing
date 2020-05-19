package com.automation.tests.day2;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ORDSTests {

    String Base_URL = "http://54.224.118.38:1000/ords/hr/";

    @Test
    @DisplayName("Get list of all employees")
    public void getAllEmployees() {

        /**response can be saved in the Response object
         * prettyPeek() - method that prints response info in nice format
         *  also the method returns Response object
         * response contains body, header and status line
         * body (payload) contains content that we requested from the web service
         * header - contains meta data
         */
        Response response = given().
                baseUri(Base_URL).
                when().
                get("/employees").prettyPeek();

        /**
         * RestAssured request has similar structure to BDD scenarios:
         *
         * given() - used for request setup and authentication
         * when() - to specify type of HTTP request: get, put, post, delete, patch, head, etc...
         * then() - to verify response, perform assertions
         */
    }
}
