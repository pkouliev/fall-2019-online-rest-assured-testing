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
        Response response = given().
                baseUri(Base_URL).
                when().
                get("/employees").prettyPeek();

    }

}
