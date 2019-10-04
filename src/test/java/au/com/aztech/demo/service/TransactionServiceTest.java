package au.com.aztech.demo.service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import au.com.aztech.demo.model.ProcessedTransactions;
import au.com.aztech.demo.model.Transaction;
import au.com.aztech.demo.repository.TransactionRepository;
import au.com.aztech.demo.transformer.TransactionTransformer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {
	
	private TransactionService testInstance;
	
	@Mock
	private TransactionTransformer mockTransactionTransformer;
	@Mock
	private TransactionRepository mockTransactionRepository;
	
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		   
		testInstance = new TransactionService(mockTransactionRepository, mockTransactionTransformer);
	}
	
	@Test
	public void shouldProcessNoTransactions() throws ParseException {
		List<String> transactionPayload = new ArrayList<String>();		
		ProcessedTransactions processedTransactions = testInstance.process(transactionPayload, 10.0);
		verify(mockTransactionTransformer, never()).transform(isA(String.class));
		
		assertEquals(0, processedTransactions.getFraudlent().size());
		assertEquals(0, processedTransactions.getInvalidRequests().size());
		assertEquals(0, processedTransactions.getProcessed().size());
	}
	
	@Test
	public void shouldFailToProcessInvalidTransaction() throws ParseException {
		List<String> transactionPayload = new ArrayList<String>();		
		transactionPayload.add("aaaa,dsasa,111");
		
		when(mockTransactionTransformer.transform(isA(String.class))).thenThrow(ParseException.class);
		
		ProcessedTransactions processedTransactions = testInstance.process(transactionPayload, 10.0);
		verify(mockTransactionTransformer, times(1)).transform(isA(String.class));
		
		assertEquals(0, processedTransactions.getFraudlent().size());
		assertEquals(1, processedTransactions.getInvalidRequests().size());
		assertEquals(0, processedTransactions.getProcessed().size());
	}

	@Test
	public void shouldIdentifyFraudulentTransaction() throws ParseException {
		List<String> transactionPayload = new ArrayList<String>();		
		transactionPayload.add("aaaa,dsasa,111");
		Transaction transaction = new Transaction();
		transaction.setAmount(100.00);
		
		when(mockTransactionTransformer.transform(isA(String.class))).thenReturn(transaction);
		
		ProcessedTransactions processedTransactions = testInstance.process(transactionPayload, 10.0);
		verify(mockTransactionTransformer, times(1)).transform(isA(String.class));
		
		assertEquals(1, processedTransactions.getFraudlent().size());
		assertEquals(0, processedTransactions.getInvalidRequests().size());
		assertEquals(0, processedTransactions.getProcessed().size());
	}

	@Test
	public void shouldProcessTransaction() throws ParseException {
		List<String> transactionPayload = new ArrayList<String>();		
		transactionPayload.add("aaaa,dsasa,10.00");
		Transaction transaction = new Transaction();
		transaction.setCreditCard("222");
		transaction.setAmount(100.00);
		transaction.setCreatedAt(LocalDateTime.now());
		
		when(mockTransactionTransformer.transform(isA(String.class))).thenReturn(transaction);
		
		ProcessedTransactions processedTransactions = testInstance.process(transactionPayload, 1000.0);
		verify(mockTransactionTransformer, times(1)).transform(isA(String.class));
		
		assertEquals(0, processedTransactions.getFraudlent().size());
		assertEquals(0, processedTransactions.getInvalidRequests().size());
		assertEquals(1, processedTransactions.getProcessed().size());
	}
}
