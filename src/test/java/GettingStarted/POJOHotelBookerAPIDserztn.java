package GettingStarted;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class POJOHotelBookerAPIDserztn extends BaseClassHotelBookerAPI{
	String token;
	String bookingid;

	@Test(priority = 0)
	public void createToken() throws JsonMappingException, JsonProcessingException {
		POJOHotelBookerAuthentication authpojo=new POJOHotelBookerAuthentication("admin",
				"password123");
		Response resp = given().spec(req)
				.body(authpojo)
				.post("/auth");

		resp.then().assertThat().spec(response_200);
		//Deserialization using Object Mapper Jackson databind
		ObjectMapper mapper=new ObjectMapper();
		POJOHotelBookerAuthenticationResp response=mapper.readValue(resp.getBody().asString(), 
									POJOHotelBookerAuthenticationResp.class);
		String token=response.getToken();
		System.out.println("The value of token is:" + token);
		//Use of jsonPath for checking the response field
//		token = resp.jsonPath().getString("token");
//		System.out.println("The value of token is:" + token);
		////Deserialization using internal Object Mapper of RestAssured
//		POJOHotelBookerAuthenticationResp responsee=resp.as(POJOHotelBookerAuthenticationResp.class);
//		String tokenn=responsee.getToken();
//		System.out.println("The value of token is:" + tokenn);
	}

	@Test(priority = 1)
	public void createBooking() throws JsonMappingException, JsonProcessingException {
		//Serialization using POJO
		POJOBookingdates bd=new POJOBookingdates();
		bd.setCheckin("2018-01-01");
		bd.setCheckout("2019-01-01");
		
		POJOHotelBookerCreateBooking create=new POJOHotelBookerCreateBooking();
		create.setFirstname("haritha");
		create.setLastname("tk");
		create.setTotalprice(111);
		create.setDepositpaid(false);
		create.setBookingdates(bd);
		create.setAdditionalneeds("Breakfast");

		Response resp = given()
				.spec(req)
				.body(create)//POJO Object sent for JSON body
				.post("/booking");
		System.out.println(resp.asPrettyString());
		resp.then().assertThat().spec(response_200);
	
		//Deserialization using Object Mapper Jackson databind
		ObjectMapper mapper=new ObjectMapper();
		POJOHotelBookerCreateBookingResp response=mapper.readValue(resp.getBody().asString(),
										POJOHotelBookerCreateBookingResp.class);
		
		System.out.println("The bookingid is:"+response.getBookingid());
		System.out.println("The firstname is:"+response.getBooking().getFirstname());
		System.out.println("The lastname is:"+response.getBooking().getLastname());
		System.out.println("The checkin is:"+response.getBooking().getBookingdates().getCheckin());
		System.out.println("The checkin is:"+response.getBooking().getBookingdates().getCheckout());
	}
	
//	@Test(priority = 2)
//	public void getBooking() {
//		
//		
//		Response resp=given()
//				.spec(req)
//				.get("/booking/"+bookingid);
//		
//		resp.then().assertThat().spec(response_200);
//		System.out.println(resp.asPrettyString());
//		
//		
//		
//	}
//	
//	@Test(priority = 3)
//	public void updateBooking() {
//		POJOBookingdates bd=new POJOBookingdates("2018-01-01", "2019-01-01");
//		POJOHotelBookerCreateBooking create1=new POJOHotelBookerCreateBooking
//				("harry","potter", 111, true,bd,"Dinner");
//		
//		Response resp=given()
//				.spec(req)
//				.header("Accept","application/json")
//				.header("Cookie","token="+token)
//				.pathParam("bookidpathparameter", bookingid)
//				.body(create1)
//				.put("/booking/{bookidpathparameter}");
//		System.out.println("The token value generated in the create token post call is:"+token);
//		resp.then().assertThat().spec(response_200);
//		System.out.println(resp.asPrettyString());
//	}
//	
//	
//	@Test(priority = 4)
//	public void partialupdateBooking() {
//		POJOHotelBookerPartialUpdate pu=new POJOHotelBookerPartialUpdate("Daniel", "Radicliff");
//		
//		Response resp=given()
//				.spec(req)
//				.header("Accept","application/json")
//				.header("Cookie","token="+token)
//				.pathParam("bookidpathparameter", bookingid)
//				.body(pu)
//				.patch("/booking/{bookidpathparameter}");
//		System.out.println("The token value generated in the create token post call is:"+token);
//		resp.then().assertThat().spec(response_200);
//		System.out.println(resp.asPrettyString());
//	}
//	
//	@Test(priority=5)
//	public void deleteBooking() {
//		
//		Response resp=given()
//		.spec(req)
//		.header("Cookie","token="+token)
//		.pathParam("bookidpathparameter", bookingid)
//		.delete("/booking/{bookidpathparameter}");
//		resp.then().assertThat().spec(response_201);
//	}

}
