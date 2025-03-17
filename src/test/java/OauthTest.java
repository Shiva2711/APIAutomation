import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.API;
import pojo.GetCourses;
import pojo.WebAutomation;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OauthTest {
	
	
	@Test
	public void OauthTest() {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//Post  API
		String response =
		given().log().all()
			.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
			.formParam("grant_type", "client_credentials")
			.formParam("scope", "trust")
		.when()
			.post("oauthapi/oauth2/resourceOwner/token")
		.then().log().all()
			.assertThat().statusCode(200)
			.extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String AccessToken = js.get("access_token");
		
		//Get CourseDetails
		GetCourses gc = given().log().all()
			.queryParam("access_token", AccessToken)
		.when()
			.get("oauthapi/getCourseDetails")
		.as(GetCourses.class);
		
		//Get LinkedIn & Instructor values
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		
		//Get Course title of first index
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		//Get price of SoapUI Webservices testing course title
		List<API> apiCourses = gc.getCourses().getApi();
		
		for(int i=0; i<apiCourses.size(); i++) {
			
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
				break;
			}
		}
		
		//Get all courseTitle of webAutomation course
		List<WebAutomation> webAutomationCourse = gc.getCourses().getWebAutomation();
		
		for(int i=0; i<webAutomationCourse.size(); i++) {
			System.out.println(webAutomationCourse.get(i).getCourseTitle());
		}
		
		
		//Verify Expected Course title is present in webAutomation course
		String courseTitle[] = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		
		ArrayList<String> a = new ArrayList<String>();
		
		for(int i=0; i<webAutomationCourse.size(); i++) {
			a.add(webAutomationCourse.get(i).getCourseTitle());
		}
		
		List<String> ExpectedList = Arrays.asList(courseTitle);
		Assert.assertTrue(a.equals(ExpectedList));
	}

}
