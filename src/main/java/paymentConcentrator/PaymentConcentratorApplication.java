package paymentConcentrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"paymentConcentrator", "paymentConcentrator2"})
public class PaymentConcentratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentConcentratorApplication.class, args);
	}
}
