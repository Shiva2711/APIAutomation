import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import PracticePojo.AddPlace_Response;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


public class Practice {
	
	
	@Test
	public void place() {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
			
		
		AddPlace_Response AddPlaceRes = 
		given()
			.log().all()
			.queryParam("key", "qaclick123")
			.header("Content-Type", "application/json")
			.header("Connection", "keep-alive")
			.body("{\r\n" + 
				" \"location\": {\r\n" + 
				" \"lat\": -38.383494,\r\n" + 
				" \"lng\": 33.427362\r\n" + 
				" },\r\n" + 
				" \"accuracy\": 50,\r\n" + 
				" \"name\": \"PG Banglore house\",\r\n" + 
				" \"phone_number\": \"(+91) 983 893 3937\",\r\n" + 
				" \"address\": \"29, side layout, cohen 09\",\r\n" + 
				" \"types\": [\r\n" + 
				" \"shoe park\",\r\n" + 
				" \"shop\"\r\n" + 
				" ],\r\n" + 
				" \"website\": \"http://google.com\",\r\n" + 
				" \"language\": \"French-IN\"\r\n" + 
				"}")
			.when()
				.post("maps/api/place/add/json")
			.then()
				.assertThat().statusCode(200).body("scope",  equalTo("APP")).extract().response().as(AddPlace_Response.class);
		
		String Status = AddPlaceRes.getStatus();
		String place_id = AddPlaceRes.getPlace_id();
		String scope = AddPlaceRes.getScope();
		String id = AddPlaceRes.getPlace_id();

		System.out.println("Status: "+Status);
		System.out.println("Status: "+place_id);
		System.out.println("Status: "+scope);
		System.out.println("Status: "+id);
	}

}
