package GettingStarted;

import java.util.concurrent.TimeUnit;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestBasicAPICalls {

	public static void main(String[] args) {
		Response resp=RestAssured.get("https://reqres.in/api/users/2");
		
		System.out.println(resp.asPrettyString());
		System.out.println(resp.getStatusCode());
		System.out.println(resp.getStatusLine());
		System.out.println(resp.contentType());
		System.out.println(resp.getTime());
		System.out.println(resp.getTimeIn(TimeUnit.SECONDS));
		System.out.println(resp.cookies());
		System.out.println(resp.headers());

	}

}
