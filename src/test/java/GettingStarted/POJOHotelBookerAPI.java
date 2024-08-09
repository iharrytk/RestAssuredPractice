package GettingStarted;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;
import java.io.File;

import io.restassured.response.Response;

public class POJOHotelBookerAPI extends BaseClassHotelBookerAPI{	
	String token;
	String bookingid;

	@Test(priority = 0)
	public void createToken() {
		POJOHotelBookerAuthentication authpojo=new POJOHotelBookerAuthentication("admin",
				"password123");

		Response resp = given().spec(req)
				.body(authpojo)
				.post("/auth");

		resp.then().assertThat().spec(response_200);

		token = resp.jsonPath().getString("token");
		System.out.println("The value of token is:" + token);

	}

	@Test(priority = 1)
	public void createBooking() {
//		POJOBookingdates bd=new POJOBookingdates("2018-01-01", "2019-01-01");
//		POJOHotelBookerCreateBooking create=new POJOHotelBookerCreateBooking
//				("haritha","tk", 111, false,bd,"Breakfast");

		Response resp = given()
				.spec(req)
//				.body(create)
				.post("/booking");
		System.out.println(resp.asPrettyString());
		resp.then().assertThat().spec(response_200);

		bookingid = resp.jsonPath().getString("bookingid");
		System.out.println("The booking id is:"+bookingid);
	}
	
	@Test(priority = 2)
	public void getBooking() {
		
		
		Response resp=given()
				.spec(req)
				.get("/booking/"+bookingid);
		
		resp.then().assertThat().spec(response_200);
		System.out.println(resp.asPrettyString());
		
		
		
	}
	
	@Test(priority = 3)
	public void updateBooking() {
//		POJOBookingdates bd=new POJOBookingdates("2018-01-01", "2019-01-01");
//		POJOHotelBookerCreateBooking create1=new POJOHotelBookerCreateBooking
//				("harry","potter", 111, true,bd,"Dinner");
		
		Response resp=given()
				.spec(req)
				.header("Accept","application/json")
				.header("Cookie","token="+token)
				.pathParam("bookidpathparameter", bookingid)
//				.body(create1)
				.put("/booking/{bookidpathparameter}");
		System.out.println("The token value generated in the create token post call is:"+token);
		resp.then().assertThat().spec(response_200);
		System.out.println(resp.asPrettyString());
	}
	
	
	@Test(priority = 4)
	public void partialupdateBooking() {
		POJOHotelBookerPartialUpdate pu=new POJOHotelBookerPartialUpdate("Daniel", "Radicliff");
		
		Response resp=given()
				.spec(req)
				.header("Accept","application/json")
				.header("Cookie","token="+token)
				.pathParam("bookidpathparameter", bookingid)
				.body(pu)
				.patch("/booking/{bookidpathparameter}");
		System.out.println("The token value generated in the create token post call is:"+token);
		resp.then().assertThat().spec(response_200);
		System.out.println(resp.asPrettyString());
	}
	
	@Test(priority=5)
	public void deleteBooking() {
		
		Response resp=given()
		.spec(req)
		.header("Cookie","token="+token)
		.pathParam("bookidpathparameter", bookingid)
		.delete("/booking/{bookidpathparameter}");
		resp.then().assertThat().spec(response_201);
	}

}
