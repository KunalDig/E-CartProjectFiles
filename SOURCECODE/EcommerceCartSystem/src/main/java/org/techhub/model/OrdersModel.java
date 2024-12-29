package org.techhub.model;

import java.time.LocalDateTime;

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
	private LocalDateTime order_date;

	public OrdersModel(int user_id, double total_price, String order_status, LocalDateTime order_date) {
		this.user_id = user_id;
		this.total_price = total_price;
		this.order_status = order_status;
		this.order_date = order_date;
	}

}
