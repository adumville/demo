package au.com.aztech.demo.repository;

import au.com.aztech.demo.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public
interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByCreditCard(String card);
	
	//  value = "select SUM(t.amount) from transaction as t where t.creditCard = :creditCard and t.createdAt > :fraudWindow")
	
	@Query(
	  value = "select COALESCE(sum(t.amount), 0) from Transaction as t where t.creditCard = :creditCard and t.createdAt >= :fraudWindow")
	Double calculateCreditCardTotalFor24HourPeriod(@Param("creditCard") String creditCard, @Param("fraudWindow")LocalDateTime fraudWindow);
}
