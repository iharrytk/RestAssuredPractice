package GettingStarted;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class DDTHotelBookerAPI extends BaseClassHotelBookerAPI {
	String token;
	String bookingid;

	@Test(priority = 0,dataProvider ="tokendata")
	public void createToken(String username,String password) {
		POJOHotelBookerAuthentication authpojo=new POJOHotelBookerAuthentication(username,password);

		Response resp = given().spec(req)
				.body(authpojo)
				.post("/auth");

		resp.then().assertThat().spec(response_200);

		token = resp.jsonPath().getString("token");
		System.out.println("The value of token is:" + token);

	}
	
	@DataProvider(name="tokendata")
	public Object[][] getToken(){
		
		Object[][] arr=new Object[1][2];
		arr[0][0] ="admin";
		arr[0][1] ="password123";
		
		return arr;
	}

	@Test(priority = 1,dataProvider = "createBookingdata")
	public void createBooking(String fname,String lname,int tp,
			boolean dp,String chkin,String chkout,String addneeds) {
//		POJOBookingdates bd=new POJOBookingdates(chkin, chkout);
//		POJOHotelBookerCreateBooking create=new POJOHotelBookerCreateBooking
//				(fname,lname, tp, dp,bd,chkout);

		Response resp = given()
				.spec(req)
//				.body(create)
				.post("/booking");
		System.out.println(resp.asPrettyString());
		resp.then().assertThat().spec(response_200);

		bookingid = resp.jsonPath().getString("bookingid");
		System.out.println("The booking id is:"+bookingid);
	}
	
	@DataProvider(name="createBookingdata")
	public Object[][] postCreateBooking(){
		Object[][] arr=new Object[3][7];
		
		arr[0][0]="Jim";
		arr[0][1]="Brown";
		arr[0][2]=111;
		arr[0][3]=true;
		arr[0][4]="2018-01-01";
		arr[0][5]="2019-01-01";
		arr[0][6]="Breakfast";
		
		
		arr[1][0]="haritha";
		arr[1][1]="tk";
		arr[1][2]=555;
		arr[1][3]=false;
		arr[1][4]="2024-01-01";
		arr[1][5]="2025-01-01";
		arr[1][6]="Dinner";
		
		arr[2][0]="jaya";
		arr[2][1]="tk";
		arr[2][2]=777;
		arr[2][3]=true;
		arr[2][4]="2023-01-01";
		arr[2][5]="2024-01-01";
		arr[2][6]="lunch";
		
		return arr;
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
