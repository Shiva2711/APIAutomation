package OAuthDemo;

import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;

public class outhTest {

	public static void main(String[] args) throws InterruptedException {
		
		/*
		 * System.setProperty("webdriver.chrome.driver",
		 * "C:\\Users\\rshiv\\RB\\API_Automation\\driver\\chromedriver.exe"); WebDriver
		 * driver = new ChromeDriver(); driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php"
		 * ); driver.findElement(By.xpath("//input[@type='email']")).sendKeys(
		 * "sivaPractiseAPI");
		 * driver.findElement(By.xpath("//span[text()='Next']")).click();
		 * Thread.sleep(3000);
		 * driver.findElement(By.xpath("//input[@type='password']")).sendKeys(
		 * "Graphql@123");
		 * driver.findElement(By.xpath("//span[text()='Next']")).click();
		 * Thread.sleep(4000); String URL = driver.getCurrentUrl();
		 */
		
		String URL ="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AQSTgQFQQ5L0mznXcpB2izp61B8HZ70ULVLMqEDv_zGyOjfJ2pgp7mmtOhseKgqFIL45qw&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		
		String code = URL.split("code=")[1].split("&scope=")[0];
		System.out.println(code);

		String accessTokenResponse = given().urlEncodingEnabled(false)
		.queryParams("code", code)
		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type", "authorization_code")
		.when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.get("access_token");
		
		String response = given().queryParam("access_token", accessToken)
		.when().log().all().get("https://rahulshettyacademy.com/getCourse.php").asString();
		
		System.out.println(response);

	}

}
