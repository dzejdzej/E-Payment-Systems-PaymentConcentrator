package paymentConcentrator.service.exception;

import paymentConcentrator.plugin.VrstaPlacanja;

public class UnsupportedPaymentMethodException extends IllegalArgumentException {

	public UnsupportedPaymentMethodException(VrstaPlacanja vrstaPlacanja) {
		super("Unsupported payment method:" + vrstaPlacanja);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

}
