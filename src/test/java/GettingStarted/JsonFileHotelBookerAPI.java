package GettingStarted;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class JsonFileHotelBookerAPI {
	
	RequestSpecification req;
	ResponseSpecification response_200;
	ResponseSpecification response_201;
	String token;
	String bookingid;

	@BeforeClass
	public void setUP() {

		System.out.println("Generating Request Specification");
 
		req = new RequestSpecBuilder()
				.setBaseUri("https://restful-booker.herokuapp.com")
				.addHeader("Content-Type", "application/json")
				.log(LogDetail.ALL)
				.build();

		System.out.println("Generated Request Specification");
		
		System.out.println("Generating Response Specification");
		response_200=new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.log(LogDetail.ALL)
		.expectStatusCode(200)
		.build();
		System.out.println("Generated Response Specification");
		
		System.out.println("Generating Response Specification");
		response_201=new ResponseSpecBuilder()
				.expectContentType(ContentType.TEXT)
				.log(LogDetail.ALL)
				.expectStatusCode(201)
				.build();
		System.out.println("Generated Response Specification");

	}

	@Test(priority = 0)
	public void createToken() {
		

		Response resp = given().spec(req)
				.body(new File("./src/test/resources/jsonfiles/usertoken.json"))
				.post("/auth");

		resp.then().assertThat().spec(response_200);

		token = resp.jsonPath().getString("token");
		System.out.println("The value of token is:" + token);

	}

	@Test(priority = 1)
	public void createBooking() {

		

		Response resp = given()
				.spec(req)
				.body(new File("./src/test/resources/jsonfiles/createbooking.json"))
				.post("/booking");
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
		
		
		Response resp=given()
				.spec(req)
				.header("Accept","application/json")
				.header("Cookie","token="+token)
				.pathParam("bookidpathparameter", bookingid)
				.body(new File("./src/test/resources/jsonfiles/updatebooking.json"))
				.put("/booking/{bookidpathparameter}");
		System.out.println("The token value generated in the create token post call is:"+token);
		resp.then().assertThat().spec(response_200);
		System.out.println(resp.asPrettyString());
	}
	
	
	@Test(priority = 4)
	public void partialupdateBooking() {
		
		
		Response resp=given()
				.spec(req)
				.header("Accept","application/json")
				.header("Cookie","token="+token)
				.pathParam("bookidpathparameter", bookingid)
				.body(new File("./src/test/resources/jsonfiles/partialupdate.json"))
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
