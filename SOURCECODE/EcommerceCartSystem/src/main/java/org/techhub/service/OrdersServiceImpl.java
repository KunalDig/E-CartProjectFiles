package org.techhub.service;

import org.techhub.model.OrderItemModel;
import org.techhub.model.OrdersModel;
import org.techhub.repository.OrdersRepository;
import org.techhub.repository.OrdersRepositoryImpl;

public class OrdersServiceImpl implements OrdersService {

	OrdersRepository orepo = new OrdersRepositoryImpl();

	public int createOrder(OrdersModel order) {
		return orepo.createOrder(order);
	}

	@Override
	public boolean addOrderItem(OrderItemModel orderItem) {
		return orepo.addOrderItem(orderItem);
	}

}
