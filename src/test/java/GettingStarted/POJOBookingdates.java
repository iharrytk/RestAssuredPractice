package GettingStarted;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class POJOBookingdates {
	private String checkin;
	private String checkout;
	
//	public POJOBookingdates(String checkin, String checkout) {
//		
//		this.checkin = checkin;
//		this.checkout = checkout;
//	}

	public String getCheckin() {
		return checkin;
	}

	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}

	public String getCheckout() {
		return checkout;
	}

	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}
		

}
