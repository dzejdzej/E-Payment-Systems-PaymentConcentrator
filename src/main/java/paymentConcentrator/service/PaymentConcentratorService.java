package paymentConcentrator.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paymentConcentrator.plugin.InitiatePaymentResponse;
import paymentConcentrator.plugin.PCNewPayment;
import paymentConcentrator.plugin.PaymentStrategy;
import paymentConcentrator.plugin.PluginRegistry;
import paymentConcentrator.rest.PCNewPaymentDTO;
import paymentConcentrator.service.exception.UnsupportedPaymentMethodException;

@Service
public class PaymentConcentratorService {

	@Autowired
	PluginRegistry pluginRegistry;

	public InitiatePaymentResponse initiatePayment(PCNewPaymentDTO pcNewPaymentDTO)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, UnsupportedPaymentMethodException {
		
		PCNewPayment payment = convertToPluginFormat(pcNewPaymentDTO);

		PaymentStrategy strategy = pluginRegistry.getPaymentStrategy(pcNewPaymentDTO.getVrstaPlacanja());
		if (strategy == null) {
			// no viable strategy found for specified VrstaPlacanja
			throw new UnsupportedPaymentMethodException(pcNewPaymentDTO.getVrstaPlacanja());
		}
		InitiatePaymentResponse response = strategy.initiatePayment(payment);

		return response;
	}
	

	private PCNewPayment convertToPluginFormat(PCNewPaymentDTO dto)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		PCNewPayment newPayment = new PCNewPayment();
		PropertyUtils.copyProperties(newPayment, dto);
		return newPayment;
	}
	
}
