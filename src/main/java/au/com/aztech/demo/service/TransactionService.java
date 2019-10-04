package au.com.aztech.demo.service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.aztech.demo.model.ProcessedTransactions;
import au.com.aztech.demo.model.Transaction;
import au.com.aztech.demo.repository.*;
import au.com.aztech.demo.transformer.TransactionTransformer;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@Service
@Transactional
@GraphQLApi
public class TransactionService {

	private TransactionRepository transactionRepository;
	private TransactionTransformer transactionTransformer;
	
	private Double priceThreshold = 1000.00; 
	
	public TransactionService(TransactionRepository transactionRepository, TransactionTransformer transactionTransformer) {
		super();
		this.transactionRepository = transactionRepository;
		this.transactionTransformer = transactionTransformer;
	}
	
	@GraphQLQuery(name = "transactions")
	public List<Transaction> getTransactions() {
		return transactionRepository.findAll();
	}
	 
	@GraphQLQuery(name = "transactionsByCreditCard")
	public List<Transaction> getTransactionByCreditCards(String creditCard) {
		return transactionRepository.findByCreditCard(creditCard);
	}
	
    @GraphQLMutation(name = "saveTransactionX")
    public ProcessedTransactions saveTransactionx(@GraphQLArgument(name = "transaction") Transaction transaction) {
    	
    	List<String> transactionPayload = new ArrayList<String>();
  
    	String payload = transaction.getCreditCard() + "," + transaction.getCreatedAt() + "," + transaction.getAmount();
    	
    	transactionPayload.add(payload);
        return process(transactionPayload, priceThreshold);
    }

    @GraphQLMutation(name = "saveTransaction")
    public Transaction saveTransaction(@GraphQLArgument(name = "transaction") Transaction transaction) {
    	
        return transactionRepository.save(transaction);
    }

	public ProcessedTransactions process(List<String> transactionPayload, Double priceThreshold) {
		
		ProcessedTransactions processedTransactions = new ProcessedTransactions();
		transactionPayload.forEach(payload->{		
			try {
				Transaction transaction = transactionTransformer.transform(payload);
				
				if(isTransactionFraudulent(transaction, priceThreshold)) {
					processedTransactions.getFraudlent().add(transaction);
				} else {
					processedTransactions.getProcessed().add(transaction);
				}				
			} catch (ParseException e) {
				processedTransactions.getInvalidRequests().add(payload);
			}	catch (NumberFormatException e) {
				processedTransactions.getInvalidRequests().add(payload);
			}	
			catch (DateTimeParseException e) {
				processedTransactions.getInvalidRequests().add(payload);
			}	
			
		});
		return processedTransactions;
	}

	private boolean isTransactionFraudulent(Transaction requestedTransaction, Double priceThreshold) {	
		if(requestedTransaction.getAmount() > priceThreshold) {
			return true;
		}
		
		List<Transaction> existingTransactions = transactionRepository.findByCreditCard(requestedTransaction.getCreditCard());
			
		if(existingTransactions == null) {		
			transactionRepository.save(requestedTransaction);
			return false;
		} else {

			LocalDateTime fraudWindow = requestedTransaction.getCreatedAt();
			Double creditCardTotal = transactionRepository.calculateCreditCardTotalFor24HourPeriod(
					requestedTransaction.getCreditCard(), 
					fraudWindow.minusHours(24));	
			
			//fraudWindow = fraudWindow.minusHours(24);
			
			//Double creditCardTotal = 0.0;
				
			//transactionRepository.calculateCreditCardTotalFor24HourPeriod(requestedTransaction, existingTransactions);	
	
			if (exceedsFraudlentThreshold(requestedTransaction, priceThreshold, creditCardTotal)) {
				return true;
			} else {
				transactionRepository.save(requestedTransaction);
				return false;
			}
		}
	}

	private boolean exceedsFraudlentThreshold(Transaction requestedTransaction, Double priceThreshold,
			Double creditCardTotal) {
		return creditCardTotal + requestedTransaction.getAmount() > priceThreshold;
	}

}
