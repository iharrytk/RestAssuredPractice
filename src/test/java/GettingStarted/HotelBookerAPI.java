package GettingStarted;

import static io.restassured.RestAssured.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class HotelBookerAPI {
	
	String token;
	String bookingid;

	@Test(priority = 0)
	public void createToken() {

		baseURI = "https://restful-booker.herokuapp.com";

		Response resp = given().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"username\" : \"admin\",\r\n" + "    \"password\" : \"password123\"\r\n" + "}")
				.post("/auth");

		Assert.assertEquals(resp.statusCode(), 200);

		token = resp.jsonPath().getString("token");
		System.out.println("The value of token is:" + token);

	}
	@Test(priority = 1)
	public void createBooking() {

		baseURI = "https://restful-booker.herokuapp.com";

		Response resp = given().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"firstname\" : \"Jim\",\r\n" + "    \"lastname\" : \"Brown\",\r\n"
						+ "    \"totalprice\" : 111,\r\n" + "    \"depositpaid\" : true,\r\n"
						+ "    \"bookingdates\" : {\r\n" + "        \"checkin\" : \"2018-01-01\",\r\n"
						+ "        \"checkout\" : \"2019-01-01\"\r\n" + "    },\r\n"
						+ "    \"additionalneeds\" : \"Breakfast\"\r\n" + "}")
				.post("/booking");
		Assert.assertEquals(resp.getStatusCode(), 200);

		bookingid = resp.jsonPath().getString("bookingid");
		System.out.println("The booking id is:"+bookingid);
	}
	
	@Test(priority = 2)
	public void getBooking() {
		baseURI="https://restful-booker.herokuapp.com";
		
		Response resp=given()
				.log()
				.all()
				.header("Content-Type","application/json")
				.get("/booking/"+bookingid);
		
		Assert.assertEquals(resp.getStatusCode(), 200);
		System.out.println(resp.asPrettyString());
		
		
		
	}
	
	@Test(priority = 3)
	public void updateBooking() {
		baseURI="https://restful-booker.herokuapp.com";
		
		Response resp=given()
				.header("Content-Type","application/json")
				.header("Accept","application/json")
				.header("Cookie","token="+token)
				.pathParam("bookidpathparameter", bookingid)
				.body("{\r\n"
						+ "    \"firstname\" : \"James\",\r\n"
						+ "    \"lastname\" : \"Brown\",\r\n"
						+ "    \"totalprice\" : 111,\r\n"
						+ "    \"depositpaid\" : true,\r\n"
						+ "    \"bookingdates\" : {\r\n"
						+ "        \"checkin\" : \"2018-01-01\",\r\n"
						+ "        \"checkout\" : \"2019-01-01\"\r\n"
						+ "    },\r\n"
						+ "    \"additionalneeds\" : \"Breakfast\"\r\n"
						+ "}")
				.put("/booking/{bookidpathparameter}");
		System.out.println("The token value generated in the create token post call is:"+token);
		Assert.assertEquals(resp.getStatusCode(), 200);
		System.out.println(resp.asPrettyString());
	}
	
	
	@Test(priority = 4)
	public void partialupdateBooking() {
		baseURI="https://restful-booker.herokuapp.com";
		
		Response resp=given()
				.header("Content-Type","application/json")
				.header("Accept","application/json")
				.header("Cookie","token="+token)
				.pathParam("bookidpathparameter", bookingid)
				.body("{\r\n"
						+ "    \"firstname\" : \"Jimmy\",\r\n"
						+ "    \"lastname\" : \"Brown\"\r\n"
						+ "}")
				.patch("/booking/{bookidpathparameter}");
		System.out.println("The token value generated in the create token post call is:"+token);
		Assert.assertEquals(resp.getStatusCode(), 200);
		System.out.println(resp.asPrettyString());
	}
	
	@Test(priority=5)
	public void deleteBooking() {
		baseURI="https://restful-booker.herokuapp.com";
		
		Response resp=given()
		.header("Content-Type","application/json")
		.header("Cookie","token="+token)
		.pathParam("bookidpathparameter", bookingid)
		.delete("/booking/{bookidpathparameter}");
		Assert.assertEquals(resp.getStatusCode(), 201);
	}
	
	

}
