import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.CreateOrder;
import pojo.GetOrderDetail;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetails;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;


public class EcommerceAPITest {

	public static void main(String[] args) {
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType("application/json").build();
		
		LoginRequest loginReq = new LoginRequest();
		loginReq.setUserEmail("postDP@gmail.com");
		loginReq.setUserPassword("Qwerty@12");
		
		RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all().spec(req).body(loginReq);
		
		LoginResponse loginRes = reqLogin.when().post("api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
		
		System.out.println(loginRes.getToken());
		String Token = loginRes.getToken();
		
		System.out.println(loginRes.getUserId());
		String UserID = loginRes.getUserId();
		
		//relaxedHTTPSValidation is used to bypass SSL certification validation
		
		//Add Product
		
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", Token).build();
		
		RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq)
		.param("productName", "Laptop")
		.param("productAddedBy", UserID)
		.param("productCategory", "electronics")
		.param("productSubCategory", "laptops")
		.param("productPrice", "34000")
		.param("productDescription", "Lenavo Laptop")
		.param("productFor", "men")
		.multiPart("productImage", new File("C:\\Users\\rshiv\\Pictures\\1kb.png"));
		
		String addProductRes = reqAddProduct.when().post("/api/ecom/product/add-product").then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(addProductRes);
		String productID = js.get("productId");
		
		//Create Order
		
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", Token).setContentType("application/json").build();
		
		OrderDetails od = new OrderDetails();
		od.setCountry("India");
		od.setProductOrderedId(productID);
		
		List<OrderDetails> orderDetailList = new ArrayList<OrderDetails>();
		orderDetailList.add(od);
		
		CreateOrder co = new CreateOrder();
		co.setOrders(orderDetailList);
		
		RequestSpecification reqCreateOrder = given().log().all().spec(createOrderBaseReq).body(co);
		GetOrderDetail resCreateOrder = reqCreateOrder.when().post("api/ecom/order/create-order").then().log().all().extract().response().as(GetOrderDetail.class);
		
		String OrderId = resCreateOrder.getOrders().get(0);
		System.out.println("Order ID: "+OrderId);
		
		String productOrderId = resCreateOrder.getProductOrderId().get(0);
		System.out.println("Product Order ID: "+productOrderId);

		
		// View Order Detail
		RequestSpecification viewOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", Token).addQueryParam("id", OrderId).build();
		
		RequestSpecification reqViewOrder = given().log().all().spec(viewOrderBaseReq);
		
		String resViewOrder = reqViewOrder.when().get("api/ecom/order/get-orders-details").then().log().all().extract().response().asString();
		
		System.out.println(resViewOrder);	
		
		
		// Delete Product
		RequestSpecification deleteProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", Token).addPathParam("productId", productID).build();
		
		RequestSpecification deleteOrder = given().log().all().spec(deleteProductBaseReq);
		
		String resDeleteOrder = deleteOrder.when().delete("api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(resDeleteOrder);
		Assert.assertEquals(js1.get("message"), "Product Deleted Successfully");
		
	}

}
