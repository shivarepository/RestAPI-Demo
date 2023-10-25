package testautomation.apitesting.tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import testautomation.apitesting.utils.FileNameConstatnts;

public class PostAPIRequestUsingTextFile {
	
	@Test
	public void postAPIRequest() throws JSONException
	{
		try {
			
			String PostAPIRequestBody
			= FileUtils.readFileToString(new File(FileNameConstatnts.Post_API_Request_JsonBody),"UTF-8");
			
	Response response = 
			RestAssured
				.given()
				.contentType(ContentType.JSON)
					.body(PostAPIRequestBody)
					.baseUri("https://restful-booker.herokuapp.com/booking")
						.when()
						.post()
							.then()
							.assertThat()
								.statusCode(200)
								.extract().response();
	
	JSONArray jsonArrayfirstName = JsonPath.read(response.body().asString(),"$.booking..firstname");
	String firstName = (String) jsonArrayfirstName.get(0);
	AssertJUnit.assertEquals(firstName, "Vineet");
	
	JSONArray jsonArraylastName = JsonPath.read(response.body().asString(),"$.booking..lastname");
	String lastName = (String) jsonArraylastName.get(0);
	AssertJUnit.assertEquals(lastName, "Garampalli");
	
	JSONArray jsonArraycheckin = JsonPath.read(response.body().asString(),"$.booking.bookingdates..checkin");
	String checkIn = (String) jsonArraycheckin.get(0);
	AssertJUnit.assertEquals(checkIn, "2021-01-01");
	
	int bookingId = JsonPath.read(response.body().asString(),"$.bookingid");
	
	RestAssured.given()
		.contentType(ContentType.JSON)
			.baseUri("https://restful-booker.herokuapp.com/booking/")
			.when()
			.get("/{bookingId}",bookingId)
			.then()
			.assertThat()
			.statusCode(200);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
