package com.automation.tests.day4;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ORDSTestsDay4 {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ORDS.URI");
    }

    /**
     * Warm-up!
     * Given accept type is JSON
     * When users sends a GET request to "/employees"
     * Then status code is 200
     * And Content type is application/json
     * And response time is less than 3 seconds
     */
    @Test
    @DisplayName("Verify status code, content type and response time")
    public void employeesTest1() {
        given().
                accept(ContentType.JSON).
                when().
                get("/employees").prettyPeek().
                then().
                assertThat().statusCode(200).
                contentType(ContentType.JSON).
                time(lessThan(3L), TimeUnit.SECONDS);
    }

    /**
     * Given accept type is JSON
     * And parameters: q = {"country_id":"US"}
     * When users sends a GET request to "/countries"
     * Then status code is 200
     * And Content type is application/json
     * And country_name from payload is "United States of America"
     */
    @Test
    @DisplayName("Verify status code, content type and country name for country with ID US")
    public void verifyCountriesTest1() {
        given().
                accept(ContentType.JSON).
                queryParam("q", "{\"country_id\":\"US\"}").
                when().
                get("/countries").
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("items[0].country_name", is("United States of America"));

        // SECOND REQUEST to list of countries
        Response response = given().
                accept(ContentType.JSON). // to request JSON from the web service
                when().
                get("/countries").prettyPeek();

        String countryName = response.jsonPath().getString("items.find{it.country_id == 'US'}.country_name");
        Map<String, Object> countryUS = response.jsonPath().get("items.find{it.country_id == 'US'}");

        // find all country names from region 2
        // collectionName.findAll{it.property.name == 'Value} -- to get collection of objects where property equals to some value
        // collectionName.findAll{it.property.name == 'Value} -- to get object where property equals to some value
        // collectionName.findAll{it.property.name == 'Value}.propertyName -- to get collection of properties where property equals to some value
        List<String> countryNames = response.jsonPath().getList("items.findAll{it.region_id == 2}.country_name");

        System.out.println("Country name: " + countryName);
        System.out.println(countryUS);
        System.out.println(countryNames);

        for (Map.Entry<String, Object> entry : countryUS.entrySet()) {
            System.out.printf("key = %s, value = %s\n", entry.getKey(), entry.getValue());
        }
    }

    // let's find employee with highest salary. Use GPath

    @Test
    public void getEmployeeTest() {
        Response response = when().get("/employees").prettyPeek();

        // collectionName.max{it.propertyName}
        Map<String, ?> bestEmployee = response.jsonPath().get("items.max{it.salary}");
        Map<String, ?> poorGuy = response.jsonPath().get("items.min{it.salary}");

        int companiesPayroll = response.jsonPath().get("items.collect{it.salary}.sum()");

        System.out.println(bestEmployee);
        System.out.println(poorGuy);
        System.out.println("Company's payroll: " + companiesPayroll);

    }
}
