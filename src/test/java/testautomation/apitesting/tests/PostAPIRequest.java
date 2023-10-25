package testautomation.apitesting.tests;

import org.testng.annotations.Test;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.minidev.json.JSONObject;

public class PostAPIRequest {
	
	@Test
	public void createBooking()
	{
		//RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		JSONObject booking = new JSONObject();
		JSONObject bookingDates =  new JSONObject();
		booking.put("firstname", "API Testing");
		booking.put("lastname", "Garampalli");
		booking.put("totalprice", 500);
		booking.put("depositpaid", true);
		booking.put("bookingdates", bookingDates);
		booking.put("additionalneeds", "Breakfast");
		
		bookingDates.put("checkin", "2020-01-01");
		bookingDates.put("checkout", "2020-01-02");
		
		Response response =
		RestAssured
		.given()
		.contentType(ContentType.JSON)
		.body(booking.toString())
		.baseUri("https://restful-booker.herokuapp.com/booking")
		.log().all()
		.when()
		.post()
		.then()
		.log().body()
		.assertThat()
		.statusCode(200)  
		.body("booking.firstname", Matchers.equalTo("API Testing"))	
		.body("booking.totalprice", Matchers.equalTo(500))
		.body("booking.bookingdates.checkin", Matchers.equalTo("2020-01-01"))  
		.extract().response();
		int bookingId = response.path("bookingid");
		
		RestAssured
		.given().contentType(ContentType.JSON)
		.pathParam("bookingID", bookingId)
		.baseUri("https://restful-booker.herokuapp.com/booking")
		.log().all()
		.when()
		.get("{bookingID}")
		.then()
		.assertThat()
		.log().body()
		.statusCode(200);		
		
		ValidatableResponse v = response.then();
		v.time(Matchers.lessThan(3000L));
	}
}
