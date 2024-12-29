package org.techhub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemModel {
	private int orderItemId;
	private int orderId;
	private int productId;
	private int quantity;
	private double price;

	public OrderItemModel(int productId, int quantity, double price) {
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
	}

}
