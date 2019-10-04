package au.com.aztech.demo.model;

import java.time.LocalDateTime;

import lombok.*;

import javax.persistence.Id;

import io.leangen.graphql.annotations.GraphQLQuery;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class Transaction {
    @Id @GeneratedValue
    @GraphQLQuery(name = "id", description = "A transactions's id")
    private Long id;
    @GraphQLQuery(name = "creditCard", description = "A transactions creditCard")
	private @NonNull String creditCard;
    @GraphQLQuery(name = "createdAt", description = "A transactions createDate")
	private @NonNull LocalDateTime createdAt;
    @GraphQLQuery(name = "amount", description = "A transactions amount")
	private @NonNull Double amount;
    @GraphQLQuery(name = "data1", description = "A transactions data1")
	private String data1;
    @GraphQLQuery(name = "data2", description = "A transactions data2")
 	private String data2;
    @GraphQLQuery(name = "data3", description = "A transactions data3")
 	private String data3;
    @GraphQLQuery(name = "data4", description = "A transactions data4")
 	private String data4;
    @GraphQLQuery(name = "data5", description = "A transactions data5")
 	private String data5;
    @GraphQLQuery(name = "data6", description = "A transactions data6")
 	private String data6;
    @GraphQLQuery(name = "data7", description = "A transactions data7")
 	private String data7;
    @GraphQLQuery(name = "data8", description = "A transactions data8")
 	private String data8;

    
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
	public String getData3() {
		return data3;
	}
	public void setData3(String data3) {
		this.data3 = data3;
	}
	public String getData4() {
		return data4;
	}
	public void setData4(String data4) {
		this.data4 = data4;
	}
	public String getData5() {
		return data5;
	}
	public void setData5(String data5) {
		this.data5 = data5;
	}
	public String getData6() {
		return data6;
	}
	public void setData6(String data6) {
		this.data6 = data6;
	}
	public String getData7() {
		return data7;
	}
	public void setData7(String data7) {
		this.data7 = data7;
	}
	public String getData8() {
		return data8;
	}
	public void setData8(String data8) {
		this.data8 = data8;
	}
}
