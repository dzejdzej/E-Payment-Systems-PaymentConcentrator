package paymentConcentrator.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

@RestController
@CrossOrigin
@RequestMapping("/paymentConcentratorMain")
public class PaymentConcentratorController {
	
	@Value("${acquirer.url}")
	private String acquirerUrl; 

	@Value("${paymentDMZ.url}")
	private String paymentDMZUrl; 
	
	@Value("${error.origin.name}")
	private String errorOriginName; 
	
	private RestTemplate rt = new RestTemplate();

	private final Log logger = LogFactory.getLog(this.getClass());
	
	@RequestMapping(value = "/processPayment/aquirer", method = RequestMethod.POST)
	public ResponseEntity<?> processPaymentAquirer(@RequestBody PCNewPaymentDTO pcNewPaymentDTO) {
		String url = "http://" + this.acquirerUrl + "/acquirerMain/requestPayment";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PCNewPaymentDTO> requestProcessPayment = new HttpEntity<>(pcNewPaymentDTO);
		
		ResponseEntity<RequestPaymentAquirerDTO> processPaymentResponse = rt.postForEntity(url , requestProcessPayment , RequestPaymentAquirerDTO.class);
		
		//////////////////////////////////////////////////////////////
		// AKO JE PROSLO OK
		// Kreirati polisu osiguranja, poslati je DC
		
		// kada to sve isto prodje
		// TEK Onda vratiti odgovor
		return ResponseEntity.ok(processPaymentResponse.getBody());
	}	

	
	@RequestMapping(value = "/completePaymentResponse", method = RequestMethod.POST)
	public ResponseEntity<?> completePaymentResponse(@RequestBody ResponseDTO responseDTO) {
		String url = "http://" + this.paymentDMZUrl + "/paymentDMZMain/completePaymentResponse";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ResponseDTO> responseProcessPayment = new HttpEntity<>(responseDTO);
		
		rt.postForEntity(url , responseProcessPayment , String.class);
		
		
		return new ResponseEntity(HttpStatus.OK);
	}	
	
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<?> exceptionHandlerHttpError(HttpClientErrorException ex) {
		String body = ex.getResponseBodyAsString();
		RestClientExceptionInfo info = new RestClientExceptionInfo(); 
		
		
		if(RestClientExceptionInfo.parse(body) == null) {
			//ova aplikacija je uzrok exceptiona
			//priprema se exception za propagiranje dalje i loguje se
			info.setOrigin(errorOriginName);
			info.setInfo(body);
		}
		else {
			info.setOrigin(RestClientExceptionInfo.parse(body).getOrigin() );
			info.setInfo(RestClientExceptionInfo.parse(body).getInfo() );
		}
		logger.error("HttpClientErrorException, info:" + RestClientExceptionInfo.toJSON(info));
		
		
		return ResponseEntity.status(ex.getStatusCode()).body(RestClientExceptionInfo.toJSON(info));
	}

}