import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		JsonPath js = new JsonPath(Payload.CoursePrice());
		
		//Print No of Courses 
		System.out.println("***Print No of Courses ***");
		int courseCount = js.getInt("courses.size()");
		System.out.println(courseCount);
		
		//Print purchase amount
		System.out.println("***Print purchase amount***");
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//Print Title of first course
		System.out.println("***Print Title of first course***");
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);
		
		//Print all the course titles and their respective prices
		System.out.println("***Print all the course titles and their respective prices***");
		for(int i=0; i<courseCount; i++) {
			String courseTitle = js.get("courses["+i+"].title");
			System.out.println(courseTitle);
			System.out.println(js.get("courses["+i+"].price").toString());
		}
		
		//Print no of copy sold by RPA course
		System.out.println("***Print no of copy sold by RPA course***");
		for(int i=0; i<courseCount; i++) {
			String courseTitle = js.get("courses["+i+"].title");
			if(courseTitle.equalsIgnoreCase("RPA")) {
				int copies = js.get("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}
		
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		System.out.println("***Verify if Sum of all Course prices matches with Purchase Amount***");
		int Sum = 0;
		for(int i=0; i<courseCount; i++) {
			int coursePrice = js.get("courses["+i+"].price");
			int copies = js.get("courses["+i+"].copies");
			int Amount = coursePrice*copies;
			Sum = Sum + Amount;
	}
		int actualTotalAmount = Sum;
		System.out.println(actualTotalAmount);
		Assert.assertEquals(actualTotalAmount, totalAmount);
	}
}
