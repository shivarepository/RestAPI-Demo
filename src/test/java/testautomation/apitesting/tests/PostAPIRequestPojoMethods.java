package testautomation.apitesting.tests;

import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import testautomation.apitesting.pojos.Booking;
import testautomation.apitesting.pojos.BookingDates;
import testautomation.apitesting.utils.FileNameConstatnts;

public class PostAPIRequestPojoMethods {

	@Test
	public void PostAPIRequest() throws IOException {
		
		try {
		
		String jsonSchema = FileUtils.readFileToString(new File(FileNameConstatnts.JSON_Schema),"UTF-8");
		BookingDates bdates = new BookingDates("2023-03-01", "2023-03-02");
		Booking booking = new Booking("Nareh", "George", "breakfast", 1000, true, bdates);
			ObjectMapper objectMapper = new ObjectMapper();
			String requestBody = objectMapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(booking);
			//System.out.println(requestBody);
			
			System.out.println(
					booking.getFirstname() +" "+ booking.getLastname()
					+" "+ booking.getTotalprice() +" "+ booking.isDepositpaid()
					+" "+ booking.getBookingdates().getCheckin() +" "+booking.getBookingdates().getCheckout()
					+" "+ booking.getAdditionalneeds()
					);
			
			Response response = RestAssured.given()
			.contentType(ContentType.JSON)
			.baseUri("https://restful-booker.herokuapp.com/booking").body(requestBody)
			.when().post().then().log().all()
			.assertThat().statusCode(200).extract().response();			
			int bookingId = response.path("bookingid");
			
//			System.out.println(jsonSchema);
			
			RestAssured.given()
			.contentType(ContentType.JSON)
			.baseUri("https://restful-booker.herokuapp.com/booking")
			.log().all()
			.when()
			.get("/{bookingId}",bookingId).then().log().all()
			.assertThat().statusCode(200)
			.body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
