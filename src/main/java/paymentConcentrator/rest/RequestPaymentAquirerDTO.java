package paymentConcentrator.rest;

public class RequestPaymentAquirerDTO {

	private String paymentURL;
	private int paymentID;

	public RequestPaymentAquirerDTO() {
	
	}
	
	public void setPaymentURL(String paymentURL) {
		this.paymentURL = paymentURL;
		
	}

	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}

	public String getPaymentURL() {
		return paymentURL;
	}

	public int getPaymentID() {
		return paymentID;
	}
	
	

}
