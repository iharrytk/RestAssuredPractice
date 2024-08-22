package GettingStarted;

import static io.restassured.RestAssured.*;
import java.io.File;

import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;

public class SchemaValidationInRestAssured {
	
	
	
	@Test
 	public void gettokenSchmaValidation() {
		
		
		given()
		.log()
		.all()
		.baseUri("https://restful-booker.herokuapp.com")
		.contentType("application/json")
		.body("{\r\n"
				+ "    \"username\" : \"admin\",\r\n"
				+ "    \"password\" : \"password123\"\r\n"
				+ "}")
		.when()
		.post("/auth")
		.then()
		.assertThat()
		.body(JsonSchemaValidator.matchesJsonSchema(new File("./src/test/resources/jsonschemas/tokenschema.json")));
	}

}
