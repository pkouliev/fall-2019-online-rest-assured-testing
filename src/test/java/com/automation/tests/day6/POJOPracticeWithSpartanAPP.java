package com.automation.tests.day6;

import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class POJOPracticeWithSpartanAPP {

    @BeforeAll
    public static void beforeALL() {
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
        authentication = basic("admin", "admin");
    }

    @Test
    public void addSpartanTest() {
        /**
         * {
         *     "gender": "Male",
         *     "name": "Adam",
         *     "phone": "8765432111"
         * }
         */

        Map<String, String> spartan = new HashMap<>();
        spartan.put("gender", "Male");
        spartan.put("name", "Captain Kirk");
        spartan.put("phone", "1234567890");

        RequestSpecification requestSpecification = given().
                auth().basic("admin", "admin").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(spartan);

        Response response = given().
                auth().basic("admin", "admin").
                contentType(ContentType.JSON).
                body(spartan).
                when().
                post("/spartans").prettyPeek();

        response.then().statusCode(201);
        response.then().body("success", is("A Spartan is Born!"));

        // deserialization
        Spartan spartanResponse = response.jsonPath().getObject("data", Spartan.class);
        Map<String, Object> spartanResponseMap = response.jsonPath().getObject("data", Map.class);

        System.out.println(spartanResponse);
        System.out.println(spartanResponseMap);

        // spartanResponse is a Spartan
        System.out.println(spartanResponse instanceof Spartan); //must be true
    }

    @Test
    @DisplayName("Retrieve existing user, update name and verify that name was updated successfully.")
    public void updateSpartanTest() {

        String name = "Captain Kirk";
        Spartan spartan = new Spartan(name, "Male", 5677890234L);

        int userToUpdate = 369;

        // get spartan from web service
        Spartan spartanToUpdate = given().
                auth().basic("admin", "admin").
                accept(ContentType.JSON).
                when().
                get("/spartans/{id}", userToUpdate).as(Spartan.class);

        System.out.println("Before update: " + spartanToUpdate);
        // change only name
        spartanToUpdate.setName(name); // update property that you need without affecting other properties
        System.out.println("After update: " + spartanToUpdate);


        // HTTP PUT request to update existence record
        //PUT - requires to provide ALL parameters in body
        // PUT requires same body as POST
        // if you miss at least one parameter, it will not work

        Response response = given().
                auth().basic("admin", "admin").
                contentType(ContentType.JSON).
                body(spartan).
                when().
                put("/spartans/{id}", userToUpdate).prettyPeek(); //request to update existing user with id 369

        response.then().statusCode(204); // verify that status code is 204 after update

        System.out.println("###########################################################");

        // to get user with id 369, the one just updated
        given().
                auth().basic("admin", "admin").
                when().
                get("/spartans/{id}", userToUpdate).prettyPeek().
                then().
                statusCode(200).body("name", is(name)); // verify that name is correct after update
    }

    @Test
    @DisplayName("Verify that user can perform PATCH request")
    public void patchUserTest() {
        //PATCH - partial update of existing record

        int userId = 369; //user to update, make sure user with this id exists

        //let's put the code to take random user
        // get all spartans
        Response response0 = given().accept(ContentType.JSON).when().get("/spartans");

        // save them all in the array list
        List<Spartan> allSpartans = response0.jsonPath().getList("", Spartan.class);
        // Spartan.class - data type of collection
        // getList - get JSON body as a list

        Random random = new Random();
        int randomNum = random.nextInt(allSpartans.size()); // generate random number

        int randomUserID = allSpartans.get(randomNum).getId();
        System.out.println("NAME BEFORE: " + allSpartans.get(randomNum).getName());

        userId = randomUserID; // to assign random user id
        System.out.println(allSpartans);

        Map<String, String> update = new HashMap<>();
        update.put("name", "Captain Grant"); // this is a request to update user

        Response response = given().
                contentType(ContentType.JSON).
                body(update).
                when().
                patch("/spartans/{id}", userId);

        response.then().assertThat().statusCode(204);

        // after we updated partially existing user, let's make sure that name is updated
        // this is a request to verify that name was updated and status code is correct as well
        given().
                accept(ContentType.JSON).
                when().get("/spartans/{id}", userId).prettyPeek().
                then().assertThat().statusCode(200).body("name", is("Captain Grant"));

    }

}
