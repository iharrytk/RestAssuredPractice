package GettingStarted;

import org.testng.annotations.BeforeClass;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseClassHotelBookerAPI {
	
	RequestSpecification req;
	ResponseSpecification response_200;
	ResponseSpecification response_201;
	
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

}
