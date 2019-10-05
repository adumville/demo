package au.com.aztech.demo.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import au.com.aztech.demo.model.ProcessedTransactions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceIntegrationTest {
	
	@Autowired
	private TransactionService transactionService;
	
	@Before
	public void before() {
//		transactionService = new TransactionService(new TransactionRepository(), new TransactionTransformer());
	}
	
	@Test 
	public void shouldProcessNoTransactions() {
		ProcessedTransactions pt = transactionService.process(new ArrayList<String>(), 1.0);
			
		assertEquals(0, pt.getFraudlent().size());
		assertEquals(0, pt.getProcessed().size());
		assertEquals(0, pt.getInvalidRequests().size());
	}
	@Test 
	public void shouldNotProcessFourTransactionsAsDataMalformed() {
		
		List<String> list = new ArrayList<String>();
		
		list.add("1a1,aa,1.0");
		list.add("1a1,,1.0");
		list.add("1a1,2019-02-10T10:10:11,AAA");
		list.add("1a1,aa,1.0");
		
		ProcessedTransactions pt = transactionService.process(list, 1.0);
			
		assertEquals(0, pt.getFraudlent().size());
		assertEquals(0, pt.getProcessed().size());
		assertEquals(4, pt.getInvalidRequests().size());
	}
	@Test 
	public void shouldProcessOneTransactions() {
		
		List<String> list = new ArrayList<String>();
		
		list.add("1a1,2019-02-10T10:10:11,10.0");
		
		ProcessedTransactions pt = transactionService.process(list, 10.0);
			
		assertEquals(0, pt.getFraudlent().size());
		assertEquals(1, pt.getProcessed().size());
		assertEquals(0, pt.getInvalidRequests().size());
	}
	
	@Test 
	public void shouldProcessTransactionsAsExceedsThreshold() {
		
		List<String> list = new ArrayList<String>();
		
		list.add("1a1,2019-02-10T10:10:11,10.1");
		
		ProcessedTransactions pt = transactionService.process(list, 10.0);
			
		assertEquals(1, pt.getFraudlent().size());
		assertEquals(0, pt.getProcessed().size());
		assertEquals(0, pt.getInvalidRequests().size());
	}	
	/*@Test 
	public void shouldProcessOneFailOneAsExceedsDailyThreshold() {
		
		List<String> list = new ArrayList<String>();
		
		list.add("1a1,2019-02-10T10:10:11,5.0");
		list.add("1a1,2019-02-10T10:10:11,5.1");
				
		ProcessedTransactions pt = transactionService.process(list, 10.0);
			
		assertEquals(1, pt.getFraudlent().size());
		assertEquals(1, pt.getProcessed().size());
		assertEquals(0, pt.getInvalidRequests().size());
	}
	*/
	/*
	@Test 
	public void shouldProcessCreditAndDebitTransactions() {
		
		List<String> list = new ArrayList<String>();
		
		list.add("1a1,2019-02-10T10:10:09,5.0");
		list.add("1a1,2019-02-10T10:10:10,-5.1");
		list.add("1a1,2019-02-10T10:10:11,5.1");
		
		ProcessedTransactions pt = transactionService.process(list, 11.0);
			
		assertEquals(0, pt.getFraudlent().size());
		assertEquals(3, pt.getProcessed().size());
		assertEquals(0, pt.getInvalidRequests().size());
	}
	*/
	@Test 
	public void shouldProcessFourTransactions() {
		
		List<String> list = new ArrayList<String>();
		
		list.add("1a1,2019-02-10T10:10:11,10.0");
		list.add("1a2,2019-02-10T10:10:11,10.0");
		list.add("1a3,2019-02-10T10:10:11,10.0");
		list.add("1a4,2019-02-10T10:10:11,10.0");
		
		ProcessedTransactions pt = transactionService.process(list, 31.0);
			
		assertEquals(0, pt.getFraudlent().size());
		assertEquals(4, pt.getProcessed().size());
		assertEquals(0, pt.getInvalidRequests().size());
	}
	/*
	@Test 
	public void shouldProcessAllSixAcrossSixDays() {
		
		List<String> list = new ArrayList<String>();
		
		list.add("1a1,2018-02-01T10:10:11,10.0");
		list.add("1a1,2018-02-02T10:10:11,10.0");
		list.add("1a1,2018-02-03T10:10:11,10.0");
		list.add("1a1,2018-02-04T10:10:11,10.0");
		list.add("1a1,2018-02-05T10:10:11,10.0");
		list.add("1a1,2018-02-06T10:10:11,10.0");
		
		ProcessedTransactions pt = transactionService.process(list, 31.0);
			
		assertEquals(0, pt.getFraudlent().size());
		assertEquals(6, pt.getProcessed().size());
		assertEquals(0, pt.getInvalidRequests().size());
	}
	@Test 
	public void shouldProcessAllSixAcrossTwoDays() {
		
		List<String> list = new ArrayList<String>();
		
		list.add("1a1,2018-02-10T10:10:11,10.0");
		list.add("1a1,2018-02-10T10:10:11,10.0");
		list.add("1a1,2018-07-10T10:10:11,10.0");
		list.add("1a1,2019-02-11T10:10:11,10.0");
		list.add("1a1,2019-02-11T10:10:11,10.0");
		list.add("1a1,2019-02-11T10:10:11,10.0");
		
		ProcessedTransactions pt = transactionService.process(list, 31.0);
			
		assertEquals(0, pt.getFraudlent().size());
		assertEquals(6, pt.getProcessed().size());
		assertEquals(0, pt.getInvalidRequests().size());
	}*/
}
