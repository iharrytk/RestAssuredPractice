package GettingStarted;

import com.fasterxml.jackson.annotation.JsonProperty;

public class POJOHotelBookerPartialUpdate {
	
	@JsonProperty("firstname")
	private String firstName;
	
	@JsonProperty("lastname")
	private String lastName;

	public POJOHotelBookerPartialUpdate(String firstName, String lastName) {
		
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

}
