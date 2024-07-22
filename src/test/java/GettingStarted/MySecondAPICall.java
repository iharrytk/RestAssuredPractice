package GettingStarted;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class MySecondAPICall {
	
	@Test
	public void getListOfUsers() {
		
		RestAssured.baseURI="https://reqres.in/api";
		Response resp=RestAssured.given().log().all().get("/users?page=2").then().log().all().extract().response();
		JsonPath js=resp.jsonPath();
		List<Integer> ls=js.getList("data.id");
		Assert.assertEquals(ls.size(), 6);
	
	}
	@Test
	public void getUserPresent() {
		RestAssured.baseURI="https://reqres.in/api";
		Response resp=RestAssured.given().log().all().get("/users?page=2").then().log().all().extract().response();
		JsonPath js=resp.jsonPath();
		
		List<String> ls=js.getList("data.email");
		boolean flag=false;
		for (String string : ls) {
			if (string.contains("george.edwards")) {
				flag=true;
				break;
				
			}
			
		}
		Assert.assertTrue(flag);
		
		
	}

}
