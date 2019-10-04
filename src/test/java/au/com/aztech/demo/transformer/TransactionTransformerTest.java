package au.com.aztech.demo.transformer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.time.format.DateTimeParseException;

import org.junit.Before;
import org.junit.Test;

import au.com.aztech.demo.model.Transaction;

public class TransactionTransformerTest {
	
	String VALID_TOKEN = "AAAaaa2345JJJ3";
	String VALID_DATE = "2019-02-10T10:10:11";
	String VALID_AMOUNT ="200";	
	
	private TransactionTransformer testInstance;
	
	@Before
	public void before() {
		testInstance = new TransactionTransformer();
	}
	
	@Test 
	public void shouldNotTransformAsNull() throws ParseException {
		Transaction transaction = testInstance.transform(null);
		assertNull(transaction);
	}
	
	@Test 
	public void shouldNotTransformAsEmpty() throws ParseException {
		Transaction transaction = testInstance.transform("");
		assertNull(transaction);
	}
	@Test 
	public void shouldTransformButWithEmptyCard() throws ParseException {
		Transaction transaction = testInstance.transform("," + VALID_DATE + "," + VALID_AMOUNT);
		
		assertNotNull(transaction);
		
		assertEquals("", transaction.getCreditCard());
		assertEquals(2019, transaction.getCreatedAt().getYear());
		assertEquals(2, transaction.getCreatedAt().getMonthValue());
		assertEquals(10, transaction.getCreatedAt().getDayOfMonth());
	}
	
	@Test (expected=DateTimeParseException.class)
	public void shouldNotTransformAsEmptyTimeStamp() throws ParseException {
		testInstance.transform(VALID_TOKEN + ",," + VALID_AMOUNT);
	}
	
	@Test (expected=DateTimeParseException.class)
	public void shouldNotTransformAsInvalidTimeStampFormat() throws ParseException {
		testInstance.transform(VALID_TOKEN + ",xyz," + VALID_AMOUNT);
	}
	
	@Test (expected=DateTimeParseException.class)
	public void shouldNotTransformAsInvalidTimeStampCharacters() throws ParseException {
		testInstance.transform(VALID_TOKEN + ",2019!-02-10T10:1A:11," + VALID_AMOUNT);
	}
	
	@Test (expected=NumberFormatException.class)
	public void shouldNotTransformAsInvalidDollarValue() throws ParseException {
		testInstance.transform("aaa,sss,");
		testInstance.transform(VALID_TOKEN + ","+ VALID_DATE + "," + "A.20");	
	}
	
	@Test (expected=NumberFormatException.class)
	public void shouldNotTransformAsInvalidCentsValue() throws ParseException {
		testInstance.transform(VALID_TOKEN + ","+ VALID_DATE + "," + "100.aa");
	}
	
	@Test
	public void shouldTransform() throws ParseException {
		Transaction transaction = testInstance.transform(VALID_TOKEN + ","+ VALID_DATE + "," + VALID_AMOUNT);
		
		assertNotNull(transaction);
		
		assertEquals(2019, transaction.getCreatedAt().getYear());
		assertEquals(2, transaction.getCreatedAt().getMonthValue());
		assertEquals(10, transaction.getCreatedAt().getDayOfMonth());
	}
}
