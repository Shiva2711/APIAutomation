import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class AddPlace_Pojo {
	
	
	@Test
	public void AddthePlace() {
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setName("Frontline house");
		p.setPhone_number("(+91) 983 893 3937");
		p.setAddress("29, side layout, cohen 09");
		p.setWebsite("http://google.com");
		p.setLanguage("Tamil-IN");
		
		location l = new location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		
		p.setLocation(l);
		
		List<String> types = new ArrayList<String>();
		types.add("shoe park");
		types.add("Shop");
		
		p.setTypes(types);
		
		
		String res =
		given()
			.queryParam("key", "qaclick123")
			.body(p)
			.header("Content-Type", "application/json")
		.when()
			.post("maps/api/place/add/json")
		.then()
			.assertThat().statusCode(200).extract().response().asString();
				
		System.out.println(res);
			
	}

}
