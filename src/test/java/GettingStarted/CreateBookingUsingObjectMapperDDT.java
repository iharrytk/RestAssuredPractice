package GettingStarted;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class CreateBookingUsingObjectMapperDDT extends BaseClassHotelBookerAPI {
	
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
	
	@Test(priority = 1)
	public void createBooking() throws StreamReadException, DatabindException, IOException {
		//Serialization using Object Mapper
		ObjectMapper create=new ObjectMapper();
		List<Map<String,Object>> values=create.readValue(new File("./src/test/resources/bulkjsonfiles/BulkUser.json")
				, new TypeReference<List<Map<String,Object>>>(){});
		
		System.out.println(values.size());//printss the size of the list
		System.out.println(values.get(2));//gets the second object in the JSON Object list
		
		for(Map<String,Object> value:values) 
		{
			System.out.println("====================create booking=====================");
			Response resp = given()
					.spec(req)
					.body(value)
					.post("/booking");
			System.out.println("The response api is:"+resp.asPrettyString());
			
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
		
	}

	
	
	
	
	

}
