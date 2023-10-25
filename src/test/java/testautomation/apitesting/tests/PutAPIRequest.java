package testautomation.apitesting.tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import testautomation.apitesting.utils.FileNameConstatnts;

public class PutAPIRequest {

	@Test
	public void putAPIRequest() throws JSONException, IOException
	{
		String TokenAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstatnts.Token_API_Request),"UTF-8");
		String PutAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstatnts.Put_API_Request_Body),"UTF-8");
		String PatchAPIRequestBody = FileUtils.readFileToString(new File(FileNameConstatnts.Patch_API_Request_Body),"UTF-8");
		int bookingId;
	//POST call		
Response response = 
	RestAssured
		.given()
		.contentType(ContentType.JSON)
			.body(PutAPIRequestBody)
			.baseUri("https://restful-booker.herokuapp.com/booking")
				.when()
				.post()
					.then()
					.assertThat()
						.statusCode(200)
						.extract().response();

JSONArray jsonArrayfirstName = JsonPath.read(response.body().asString(),"$.booking..firstname");
String firstName = (String) jsonArrayfirstName.get(0);
AssertJUnit.assertEquals(firstName, "Shivanagayya");

bookingId = JsonPath.read(response.body().asString(),"$.bookingid");
	
	//GET call
RestAssured.given()
.contentType(ContentType.JSON)
	.baseUri("https://restful-booker.herokuapp.com/booking/")
	.when()
	.get("/{bookingId}",bookingId)
	.then()
	.assertThat()
	.statusCode(200);
	
	//Token generation
		Response tokenAPIResponse =
		RestAssured.given().contentType(ContentType.JSON).baseUri("https://restful-booker.herokuapp.com/auth")
		.body(TokenAPIRequestBody).when().post().then().assertThat().statusCode(200).extract().response();
		
		String token = JsonPath.read(tokenAPIResponse.body().asString(),"$.token");
		
		RestAssured.given().contentType(ContentType.JSON)
		.body(PutAPIRequestBody).header("Cookie","token=" +token)
		.baseUri("https://restful-booker.herokuapp.com/booking")
		.when().put("/{bookingId}",bookingId).then().assertThat().statusCode(200)
		.body("firstname", Matchers.equalTo("Shivanagayya"))
		.body("lastname", Matchers.equalTo("Garampalli"));
		
		RestAssured.given().contentType(ContentType.JSON)
		.header("Cookie","token="+token).baseUri("https://restful-booker.herokuapp.com/booking")
		.body(PatchAPIRequestBody)
		.when().patch("/{bookingId",bookingId).then().assertThat().statusCode(200);
		
		}
}