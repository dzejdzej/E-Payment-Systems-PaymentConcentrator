package paymentConcentrator.rest;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import paymentConcentrator.plugin.InitiatePaymentResponse;
import paymentConcentrator.plugin.PaymentStrategy;
import paymentConcentrator.plugin.PluginRegistry;
import paymentConcentrator.service.PaymentConcentratorService;
import paymentConcentrator.service.exception.UnsupportedPaymentMethodException;

@RestController
@CrossOrigin
@RequestMapping("/paymentConcentratorMain")
public class PaymentConcentratorController {
	
	

	@Value("${paymentDMZ.url}")
	private String paymentDMZUrl; 
	
	@Value("${error.origin.name}")
	private String errorOriginName; 
	
	@Autowired
	private RestTemplate rt;

	private final Log logger = LogFactory.getLog(this.getClass());

	
	@Autowired
	PaymentConcentratorService paymentConcentratorService;
	
	@RequestMapping(value = "/processPayment", method = RequestMethod.POST)
	public ResponseEntity<?> processPaymentAquirer(@RequestBody PCNewPaymentDTO pcNewPaymentDTO) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		InitiatePaymentResponse response = paymentConcentratorService.initiatePayment(pcNewPaymentDTO);
		// kada to sve isto prodje
		// TEK Onda vratiti odgovor
		return ResponseEntity.ok(response);
	}	
	
	@ExceptionHandler(UnsupportedPaymentMethodException.class)
	public ResponseEntity<?> handleUnsupportedMethod(UnsupportedPaymentMethodException ex) {
		logger.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	
	@RequestMapping(value = "/completePaymentResponse", method = RequestMethod.POST)
	public ResponseEntity<?> completePaymentResponse(@RequestBody ResponseDTO responseDTO) {
		String url = "https://" + this.paymentDMZUrl + "/paymentDMZMain/completePaymentResponse";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<ResponseDTO> responseProcessPayment = new HttpEntity<>(responseDTO);
		
		rt.postForEntity(url , responseProcessPayment , String.class);
		
		
		return new ResponseEntity(HttpStatus.OK);
	}	
	
	
	

}