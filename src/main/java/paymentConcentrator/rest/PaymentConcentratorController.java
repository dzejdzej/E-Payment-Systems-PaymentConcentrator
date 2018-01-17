package paymentConcentrator.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
@RequestMapping("/paymentConcentratorMain")
public class PaymentConcentratorController {
	
	@Value("${acquirer.url}")
	private String acquirerUrl; 

	private RestTemplate rt = new RestTemplate();
	
	@RequestMapping(value = "/processPayment/aquirer", method = RequestMethod.POST)
	public ResponseEntity<?> processPaymentAquirer(@RequestBody PCNewPaymentDTO pcNewPaymentDTO) {
		String url = "http://" + this.acquirerUrl + "/aquirerMain/requestPayment";
		
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
}