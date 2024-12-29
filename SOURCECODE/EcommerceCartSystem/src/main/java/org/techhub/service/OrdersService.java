package org.techhub.service;

import org.techhub.model.OrderItemModel;
import org.techhub.model.OrdersModel;

public interface OrdersService {
	public int createOrder(OrdersModel order);

	public boolean addOrderItem(OrderItemModel orderItem);
}
