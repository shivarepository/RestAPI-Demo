package testautomation.apitesting.tests;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetAPIRequest {

	@Test
	public void getAllbookings()
	{
		Response response = 
		 RestAssured
		.given()
		.contentType(ContentType.JSON)
		.baseUri("https://restful-booker.herokuapp.com/booking")
		.when()
		.get()
		.then()
		.assertThat()
		.statusCode(200)   
		.header("Content-Type", "application/json; charset=utf-8")
		.extract()
		.response();
		
		AssertJUnit.assertTrue(response.getBody().asString().contains("bookingid"));
	}
}
