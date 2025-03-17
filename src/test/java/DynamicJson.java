

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	
	@Test(dataProvider = "Bookdata")
	public void addBook(String isbn, String aisle) {
		
		RestAssured.baseURI = "http://216.10.245.166";
		
		//Addbooks
		String response = given().header("Content-Type", "application/json").body(Payload.Addbook(isbn, aisle))
		.when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
		
		//DeleteBooks
		given().header("Content-Type", "application/json").body(Payload.Deletebook(id))
		.when().post("Library/DeleteBook.php")
		.then().assertThat().statusCode(200);
	}
	
	@DataProvider (name ="Bookdata")
	public Object[][] getData() {
		
		//Array = Collection of element
		//MutiDimension Array = Collection of Array
		return new Object[][] {{"sdf", "2323"}, {"asd", "5423"}, {"wff", "123213"}, {"sadf", "123"}};
		
	}

	
}
