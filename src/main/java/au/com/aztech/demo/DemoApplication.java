package au.com.aztech.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import au.com.aztech.demo.service.TransactionService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	ApplicationRunner init(TransactionService transactionService) {	 
	 	List<String> transactionPayload = new ArrayList<String>();
         
	 	transactionPayload.add("1a1,2018-02-01T10:10:11,10.0");
		transactionPayload.add("1a1,2018-02-02T10:10:11,10.0");
		transactionPayload.add("1a1,2018-02-03T10:10:11,10.0");
		transactionPayload.add("1a1,2018-02-04T10:10:11,10.0");
		transactionPayload.add("1a1,2018-02-05T10:10:11,10.0");
		transactionPayload.add("1a1,2018-02-06T10:10:11,10.0");
		
		transactionService.process(transactionPayload, 10000.00);
		return null;
   }
 
}
