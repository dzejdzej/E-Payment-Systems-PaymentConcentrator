package paymentConcentrator.plugin;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PluginRegistry {
	
	@Autowired
    private List<PaymentStrategy> paymentStragies;

    
    public PaymentStrategy getPaymentStrategy(VrstaPlacanja vrstaPlacanja) {
        for(PaymentStrategy strategy : paymentStragies) {
        	if(strategy.supportsVrstaPlacanja(vrstaPlacanja)) {
        		return strategy;
        	}
        }
        
        return null;
        
    }
    
    
}
