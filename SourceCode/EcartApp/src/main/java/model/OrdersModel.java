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

public class OrdersModel {

	private int order_id;
	private int user_id;
	private double total_price;
	private String order_status;
	private Timestamp order_date;

}
