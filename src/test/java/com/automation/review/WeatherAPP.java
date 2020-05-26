package com.automation.review;

import io.restassured.response.Response;

import java.util.Scanner;

import static io.restassured.RestAssured.*;

public class WeatherAPP {


    public static void main(String[] args) {
        //get("search/?query=san").prettyPeek();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city name: ");
        String city = scanner.nextLine();
        String woeid = getWOEID(city);
        printWeatherInfo(woeid);
    }

    static {
        baseURI = "https://www.metaweather.com/api/location";
    }

    public static String getWOEID(String city) {
        Response response = given().queryParam("query", city).get("/search");
        String woeid = response.jsonPath().getString("woeid");
        System.out.println("WOEID :: " + woeid);
        return woeid;
    }

    public static void printWeatherInfo(String woeid) {
        woeid = woeid.replaceAll("\\D", ""); //to delete all non-digits
        Response response = get("{woeid}", woeid).prettyPeek();
    }
}
