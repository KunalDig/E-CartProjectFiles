package org.techhub.repository;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.techhub.model.OrderItemModel;
import org.techhub.model.OrdersModel;

public class OrdersRepositoryImpl extends DBSTATE implements OrdersRepository {

	private static Logger logger = Logger.getLogger(OrdersRepositoryImpl.class);
	static {
		PropertyConfigurator.configure(
				"C:\\Users\\abcd\\Desktop\\Project\\EcommerceCartSystem\\src\\main\\resources\\application.properties");
	}

	public int createOrder(OrdersModel order) {
		int orderId = -1;

		if (order == null) {
			logger.error("Order object is null.");
			return orderId;
		}

		// checking order fields
		logger.info("User ID: " + order.getUser_id());
		logger.info("Total Price: " + order.getTotal_price());
		logger.info("Order Status: " + order.getOrder_status());
		logger.info("Order Date: " + order.getOrder_date());

		try {

			if (conn == null) {
				logger.error("Database connection is null.");
				return orderId;
			}

			stmt = conn.prepareStatement(
					"insert into orders (user_id, total_price, order_status, order_date) values(?,?,?,?)");
			stmt.setInt(1, order.getUser_id());
			stmt.setDouble(2, order.getTotal_price());
			stmt.setString(3, order.getOrder_status());

			if (order.getOrder_date() == null) {
				logger.error("Order date is null.");
				return orderId;
			}

			stmt.setTimestamp(4, Timestamp.valueOf(order.getOrder_date()));

			int result = stmt.executeUpdate();

			if (result > 0) {
				tempStmt = conn.prepareStatement(
						"select order_id from orders where user_id = ? order by order_date desc limit 1");
				tempStmt.setInt(1, order.getUser_id());
				itemRs = tempStmt.executeQuery();

				if (itemRs.next()) {
					orderId = itemRs.getInt("order_id");
				}
			}
		} catch (SQLException e) {
			logger.error("Failed to create order: " + e.getMessage());
		}
		return orderId;
	}

	public boolean addOrderItem(OrderItemModel orderItem) {

		try {
			stmt = conn.prepareStatement(
					"insert into order_item (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)");

			stmt.setInt(1, orderItem.getOrderId());
			stmt.setInt(2, orderItem.getProductId());
			stmt.setInt(3, orderItem.getQuantity());
			stmt.setDouble(4, orderItem.getPrice());

			int result = stmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			logger.error("Failed to add order item: " + e.getMessage(), e);
			return false;
		}
	}

}
