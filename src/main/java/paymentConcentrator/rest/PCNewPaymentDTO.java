package paymentConcentrator.rest;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class PCNewPaymentDTO {
	
	private String merchantId;

	private String merchantPassword;

    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal ammount;
	
    private int merchantOrderId;
    
    private Date merchantTimestamp;
    
	private String errorURL;
	
	public PCNewPaymentDTO() {
	
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantPassword() {
		return merchantPassword;
	}

	public void setMerchantPassword(String merchantPassword) {
		this.merchantPassword = merchantPassword;
	}

	public BigDecimal getAmmount() {
		return ammount;
	}

	public void setAmmount(BigDecimal ammount) {
		this.ammount = ammount;
	}

	public int getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(int merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}

	public Date getMerchantTimestamp() {
		return merchantTimestamp;
	}

	public void setMerchantTimestamp(Date merchantTimestamp) {
		this.merchantTimestamp = merchantTimestamp;
	}

	public String getErrorURL() {
		return errorURL;
	}

	public void setErrorURL(String errorURL) {
		this.errorURL = errorURL;
	}
	
	

}