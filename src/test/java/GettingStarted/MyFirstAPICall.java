package GettingStarted;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class MyFirstAPICall {

	@Test
	public void getUserTest() {
		RestAssured.baseURI = "https://reqres.in/api";
		Response resp = RestAssured.given().get("/users/2");
		Assert.assertEquals(resp.getStatusCode(), 200);
		Assert.assertTrue(resp.getStatusLine().contains("OK"));
		Assert.assertTrue(resp.contentType().contains("json"));

	}

	@Test
	public void getUserResponseFieldsTest() {

		RestAssured.baseURI = "https://reqres.in/api";
		Response resp = RestAssured.given().get("/users/2");
		JsonPath js = resp.jsonPath();
		int id = js.getInt("data.id");
		System.out.println("The id field captured in the JSON response:" + id);
		Assert.assertEquals(id, 2);
	}
	
	
	@Test
	public void verifySingleUserDetailsTest() {
		RestAssured.baseURI="https://reqres.in/api";
		Response resp=RestAssured.given().get("/users/2");
		JsonPath js=resp.jsonPath();
		int idfield=js.getInt("data.id");
		String emailfield=js.getString("data.email");
		String firstnamefield=js.getString("data.first_name");
		String lastnamefield=js.getString("data.last_name");
		String avatarfield=js.getString("data.avatar");
		String urlfield=js.getString("support.url");
		String textfield=js.getString("support.text");
		 
		Assert.assertEquals(idfield, 2);
		Assert.assertTrue(emailfield.endsWith("reqres.in"));
		Assert.assertEquals(firstnamefield, "Janet");
		Assert.assertEquals(lastnamefield, "Weaver");
		Assert.assertTrue(avatarfield.contains("reqres.in"));
		Assert.assertTrue(urlfield.contains("reqres.in"));
		Assert.assertEquals(textfield, "To keep ReqRes free, contributions towards server costs are appreciated!");
		
	}
	
	
	@Test
	public void createUserTest() {
		RestAssured.baseURI="https://reqres.in/api";
		Response resp=RestAssured.given().log().all().body("{\r\n"
				+ "    \"name\": \"morpheus\",\r\n"
				+ "    \"job\": \"leader\"\r\n"
				+ "}").post("/users").then().log().all().extract().response();
		System.out.println(resp.asPrettyString());
		System.out.println(resp.statusCode());
		System.out.println(resp.statusLine());
		
		
	}
	
	

	@Test
	public void putUserTest() {
		RestAssured.baseURI="https://reqres.in/api";
		Response resp=RestAssured.given().log().all().body("{\r\n"
				+ "    \"name\": \"morpheus\",\r\n"
				+ "    \"job\": \"zion resident\"\r\n"
				+ "}").put("/users/2").then().log().all().extract().response();
		System.out.println(resp.asPrettyString());
		System.out.println(resp.statusCode());
		System.out.println(resp.statusLine());
		Assert.assertEquals(resp.statusCode(), 200);
		
		
	}
	
	@Test
	public void patchUserTest() {
		RestAssured.baseURI="https://reqres.in/api";
		Response resp=RestAssured.given().log().all().body("{\r\n"
				+ "    \"name\": \"morpheus\",\r\n"
				+ "    \"job\": \"zion resident\"\r\n"
				+ "}").patch("/users/2").then().log().all().extract().response();
		System.out.println(resp.asPrettyString());
		System.out.println(resp.statusCode());
		System.out.println(resp.statusLine());
		Assert.assertEquals(resp.statusCode(), 200);
		
		 
	}
	
	@Test
	public void deleteUserTest() {
		RestAssured.baseURI = "https://reqres.in/api";
		Response resp = RestAssured.given().log().all().delete("/users/2").then().log().all().extract().response();
		Assert.assertEquals(resp.getStatusCode(), 204);
		

	}
	
	
	

}
