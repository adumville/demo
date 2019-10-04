package au.com.aztech.demo.transformer;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import au.com.aztech.demo.model.Transaction;

@Service
public class TransactionTransformer {
			
	public Transaction transform(String payload) throws ParseException {	
		Transaction transaction = null;
      	
		if(payload == null) {
			return transaction;			
		}
    	String[] elements = payload.split(",");

    	if(elements.length == 3) {
	        	transaction = new Transaction();
	        	transaction.setCreditCard(elements[0]);	        	
	        	transaction.setCreatedAt(buildDate(elements[1]));
	        	transaction.setAmount(buildAmount(elements[2]));
    	}
		return transaction;
	}
	
	private LocalDateTime buildDate(String dateValue) throws ParseException {
		
		return LocalDateTime.parse(dateValue, DateTimeFormatter.ISO_DATE_TIME);
	}
	
	private Double buildAmount(String amountValue)  {
		return Double.valueOf(amountValue);
	}
}
