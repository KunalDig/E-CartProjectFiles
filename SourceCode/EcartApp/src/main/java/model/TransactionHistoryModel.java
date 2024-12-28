package model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionHistoryModel {

	
	private int transaction_id;
	private int order_id;
	private int user_id;
	private double amount;
	private Timestamp transaction_date;
	private String payment_method;
	private String status;
}
