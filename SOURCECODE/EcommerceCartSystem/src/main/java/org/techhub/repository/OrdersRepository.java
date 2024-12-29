package org.techhub.repository;

import org.techhub.model.OrderItemModel;
import org.techhub.model.OrdersModel;

public interface OrdersRepository {
	public int createOrder(OrdersModel order);

	public boolean addOrderItem(OrderItemModel orderItem);
}
