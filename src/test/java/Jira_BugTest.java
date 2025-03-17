import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;


public class Jira_BugTest {
	
	@Test
	public void BugTest() {
		
		RestAssured.baseURI = "https://shivarcse.atlassian.net";
		
		//Create an issue
		String CreateIssue = 
				given()
					.header("Content-Type", "application/json").
					header("Authorization", "Basic c2hpdmEucmNzZUBnbWFpbC5jb206QVRBVFQzeEZmR0YwY1hoRDBnSndnT253ZWkxX01sal83OUtUVmNMTi14dDFDbDRLSGwtaDNjbGNwak50dmFKYm55elFWRnF1YmJ2Rzh2aDlaU05yU3RjRU1JWmg4ek80a2FyRVVUcFFqSFQ2UURfYWxqNF9fbGpaVzVXeUxobTMtU2Q0em1QUU8wRDFYd3F1NjVfbFQyWFgzOWpXV3ZnOEIwRndoMXMyVjZGNHBoVVJJNFNoRjBNPTQ3NTMzNzg5")
					.body("{\r\n" + 
							"    \"fields\": {\r\n" + 
							"       \"project\":\r\n" + 
							"       {\r\n" + 
							"          \"key\": \"SCRUM\"\r\n" + 
							"       },\r\n" + 
							"       \"summary\": \"Menu is not working - Automation\",\r\n" + 
							"       \"issuetype\": {\r\n" + 
							"          \"name\": \"Bug\"\r\n" + 
							"       }\r\n" + 
							"   }\r\n" + 
							"}")
					.log().all()
		.when()
			.post("rest/api/2/issue")
		.then()
			.assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(CreateIssue);
		
		String id = js.getString("id");
		System.out.println(id);
		
		
		// Add Attachment 
		given()
			.header("X-Atlassian-Token", "no-check")
			.header("Authorization", "Basic c2hpdmEucmNzZUBnbWFpbC5jb206QVRBVFQzeEZmR0YwY1hoRDBnSndnT253ZWkxX01sal83OUtUVmNMTi14dDFDbDRLSGwtaDNjbGNwak50dmFKYm55elFWRnF1YmJ2Rzh2aDlaU05yU3RjRU1JWmg4ek80a2FyRVVUcFFqSFQ2UURfYWxqNF9fbGpaVzVXeUxobTMtU2Q0em1QUU8wRDFYd3F1NjVfbFQyWFgzOWpXV3ZnOEIwRndoMXMyVjZGNHBoVVJJNFNoRjBNPTQ3NTMzNzg5")
			.pathParam("key", id)
			.multiPart("file", new File("C:\\Users\\rshiv\\Pictures\\download.jfif"))
			.log().all()
		.when()
			.post("rest/api/2/issue/{key}/attachments")
		.then()
			.log().all()
			.assertThat().statusCode(200);
		
		//Get Issue
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Basic c2hpdmEucmNzZUBnbWFpbC5jb206QVRBVFQzeEZmR0YwY1hoRDBnSndnT253ZWkxX01sal83OUtUVmNMTi14dDFDbDRLSGwtaDNjbGNwak50dmFKYm55elFWRnF1YmJ2Rzh2aDlaU05yU3RjRU1JWmg4ek80a2FyRVVUcFFqSFQ2UURfYWxqNF9fbGpaVzVXeUxobTMtU2Q0em1QUU8wRDFYd3F1NjVfbFQyWFgzOWpXV3ZnOEIwRndoMXMyVjZGNHBoVVJJNFNoRjBNPTQ3NTMzNzg5")
			.pathParam("key", id)
			.log().all()
		.when()
			.get("rest/api/2/issue/{key}")
		.then()
			.log().all()
			.assertThat().statusCode(200);
			
	}
}
