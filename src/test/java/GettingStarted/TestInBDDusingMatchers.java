package GettingStarted;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

public class TestInBDDusingMatchers {
	
	@Test
	public void getUsers() {
		given()
		.queryParam("page", "2")
		.baseUri("https://reqres.in/api")
		.log()
		.all()
		.when()
		.get("/users")
		.then()
		.body("data.email",everyItem(containsString("reqres.in")))
		.body("data.id", hasItem(11));
		
	}

}
