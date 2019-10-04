package au.com.aztech.demo.model;

import java.util.ArrayList;
import java.util.List;

public class ProcessedTransactions {
	List<String> invalidRequests = new ArrayList<String>();
	List<Transaction> processed  = new ArrayList<Transaction>();
	List<Transaction> fraudlent = new ArrayList<Transaction>();
	
	public List<String> getInvalidRequests() {
		return invalidRequests;
	}
	public void setInvalidRequests(List<String> invalidRequests) {
		this.invalidRequests = invalidRequests;
	}
	public List<Transaction> getProcessed() {
		return processed;
	}
	public void setProcessed(List<Transaction> processed) {
		this.processed = processed;
	}
	public List<Transaction> getFraudlent() {
		return fraudlent;
	}
	public void setFraudlent(List<Transaction> fraudlent) {
		this.fraudlent = fraudlent;
	}
}
