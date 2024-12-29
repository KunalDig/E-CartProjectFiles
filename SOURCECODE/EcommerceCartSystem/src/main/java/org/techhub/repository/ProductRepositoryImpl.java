package org.techhub.repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.techhub.model.OrdersModel;
import org.techhub.model.ProductModel;
import org.techhub.model.UserModel;

public class ProductRepositoryImpl extends DBSTATE implements ProductRepository {

	private static Logger logger = Logger.getLogger(ProductRepositoryImpl.class);

	static {
		PropertyConfigurator.configure(
				"C:\\Users\\abcd\\Desktop\\Project\\EcommerceCartSystem\\src\\main\\resources\\application.properties");
	}

	// List<ProductModel> list = new ArrayList<ProductModel>();

	public boolean addNewProduct(ProductModel prod) {
		try {
			stmt = conn.prepareStatement("insert into products values('0',?,?,?,?,?)");
			stmt.setString(1, prod.getName());
			stmt.setString(2, prod.getDescription());
			stmt.setDouble(3, prod.getPrice());
			stmt.setInt(4, prod.getStockQuantity());
			stmt.setTimestamp(5, Timestamp.valueOf(prod.getCreatedAt()));
			int value = stmt.executeUpdate();
			return value > 0 ? true : false;
		} catch (Exception ex) {
			logger.error("Error adding new product: " + ex.getMessage(), ex);
		}
		return false;
	}

	public Optional<List<ProductModel>> getAllProducts() {
		List<ProductModel> list = new ArrayList<ProductModel>();
		try {
			stmt = conn.prepareStatement("select * from products");
			rs = stmt.executeQuery();

			while (rs.next()) {
				ProductModel prod = new ProductModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4),
						rs.getInt(5), rs.getTimestamp(6).toLocalDateTime());
				list.add(prod);
			}
			return list.isEmpty() ? Optional.empty() : Optional.of(list);
		} catch (Exception e) {
			logger.error("Error retrieving all products: " + e.getMessage(), e);
			return null;
		}

	}

	public ProductModel getProductByName(String prodName) {
		try {
			stmt = conn.prepareStatement("select * from products where name = ?");
			stmt.setString(1, prodName);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return new ProductModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5),
						rs.getTimestamp(6).toLocalDateTime());
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Error getting product by name: " + e.getMessage(), e);
			return null;
		}

	}

	public boolean updateProduct(ProductModel model, String fieldToUpdate, Object newValue) {
		try {
			if (!isValidField(fieldToUpdate)) {
				System.out.println("Invalid field name. Please choose a valid field.");
				return false;
			}

			if (!isProductPresent(model)) {
				System.out.println("Product with ID " + model.getProductId() + " does not exist.");
				return false;
			}

			String updateQuery = "update products set " + fieldToUpdate + " = ? where product_id = ?";
			stmt = conn.prepareStatement(updateQuery);
			stmt.setObject(1, newValue); // `setObject` allows flexibility for different data types
			stmt.setInt(2, model.getProductId());
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated > 0) {
//				System.out.println("Product updated successfully!");
				return true;
			} else {
				logger.info("Product cannot be updated ");
				return false;
			}

		} catch (Exception e) {
			logger.info("Product cannot be Updated " + e);
			return false;
		}
	}

	public boolean isValidField(String field) {
		// List of valid fields that can be updated
		String[] validFields = { "name", "description", "price", "stock_quantity" };
		for (String validField : validFields) {
			if (validField.equalsIgnoreCase(field)) {
				return true;
			}
		}
		return false;
	}

	public boolean isProductPresent(ProductModel model) {

		try {
			String query = "SELECT COUNT(*) FROM products WHERE product_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, model.getProductId());
			rs = stmt.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				return true;
			}
		} catch (SQLException e) {
			logger.info("some error appears during product search.");
		}
		return false;
	}

	public boolean deleteProduct(ProductModel model) {
		try {
			logger.info("delete product method is called.");
			// Check if the product exists
			if (!isProductPresent(model)) {
				System.out.println("Product with ID " + model.getProductId() + " does not exist.");
				return false;
			}
			// SQL query to delete the product
			String deleteQuery = "delete from products where product_id = ?";
			stmt = conn.prepareStatement(deleteQuery);
			stmt.setInt(1, model.getProductId());
			int rowsDeleted = stmt.executeUpdate();
			if (rowsDeleted > 0) {
//                System.out.println("Product deleted successfully!");
				return true;
			} else {
//                System.out.println("Failed to delete the product.");
				return false;
			}
		} catch (Exception e) {
			logger.info("Product cannot be deleted " + e);
			return false;
		}
	}

	public void viewStock(ProductModel model) {
		try {
			logger.info("view stock method is called.");
			// Check if the product exists
			if (!isProductPresent(model)) {
				System.out.println("Product with ID " + model.getProductId() + " does not exist.");
				return;
			}
			String query = "select name, stock_quantity from products where product_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, model.getProductId());
			rs = stmt.executeQuery();
			if (rs.next()) {
				String productName = rs.getString("name");
				int stockQuantity = rs.getInt("stock_quantity");

				System.out.println("Product Name: " + productName);
				System.out.println("Stock Quantity: " + stockQuantity);
			}
		} catch (Exception e) {
			logger.error("cannot view stock." + e);
		}
	}

	public void viewAllOrders() {
		try {
			logger.info("viewAllOrders method is called.");
			String query = "SELECT o.order_id, o.user_id, u.username, o.total_price, o.order_status,  o.order_date FROM orders o  INNER JOIN users u ON o.user_id = u.user_id ";

			String itemQuery = "SELECT oi.order_id, p.name AS product_name, oi.quantity, oi.price  FROM order_item oi INNER JOIN products p ON oi.product_id = p.product_id WHERE oi.order_id = ?";

			boolean ordersFound = false;
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();

			System.out.printf("%-10s %-15s %-20s %-15s %-15s %-20s%n", "Order ID", "User ID", "Username", "Total Price",
					"Status", "Order Date");
			System.out
					.println("---------------------------------------------------------------------------------------");

			while (rs.next()) {
				ordersFound = true; // Mark that we found at least one order
				int orderId = rs.getInt("order_id");
				int userId = rs.getInt("user_id");
				String username = rs.getString("username");
				double totalPrice = rs.getDouble("total_price");
				String status = rs.getString("order_status");
				String orderDate = rs.getTimestamp("order_date").toString();

				System.out.printf("%-10d %-15d %-20s %-15.2f %-15s %-20s%n", orderId, userId, username, totalPrice,
						status, orderDate);

				tempStmt = conn.prepareStatement(itemQuery);
				tempStmt.setInt(1, orderId);
				itemRs = tempStmt.executeQuery();
				System.out.printf("%-10s %-30s %-10s %-15s%n", "", "Product Name", "Quantity", "Price");
				while (itemRs.next()) {
					String productName = itemRs.getString("product_name");
					int quantity = itemRs.getInt("quantity");
					double price = itemRs.getDouble("price");

					System.out.printf("%-10s %-30s %-10d %-15.2f%n", "", productName, quantity, price);
				}
				System.out.println(
						"---------------------------------------------------------------------------------------");
			}

			if (!ordersFound) {
				System.out.println("No orders found in the database.");
			}

		} catch (Exception e) {
			logger.error("cannot view order details." + e);
		}
	}

	public boolean updateOrderStatus(OrdersModel model, String newStatus) {
		try {
			logger.info("updateOrderStatus method is called.");
			// Validate the new status
			if (!isValidOrderStatus(newStatus)) {
				System.out.println(
						"Invalid order status. Allowed values: Pending, Processing, Shipped, Delivered, Cancelled.");
				return false;
			}

			// Check if the order exists
			if (!isOrderPresent(model)) {
				System.out.println("Order with ID " + model.getOrder_id() + " does not exist.");
				return false;
			}

			String updateQuery = "update orders set order_status = ? where order_id = ?";
			stmt = conn.prepareStatement(updateQuery);
			stmt.setString(1, newStatus);
			stmt.setInt(2, model.getOrder_id());
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated > 0) {
				logger.info("order status updated");
				return true;
			} else {
				logger.warn("Some problem in updating status.");
			}
		} catch (Exception e) {
			logger.error("Error in updateOrderStatus ", e);
		}
		return false;
	}

	public boolean isOrderPresent(OrdersModel model) {
		try {
			String query = "select count(*) from orders where order_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, model.getOrder_id());
			rs = stmt.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				return true; // Order exists
			}
		} catch (Exception e) {
			logger.error("Something went wrong in isOrderPresent :" + e);
		}
		return false;
	}

	public boolean isValidOrderStatus(String status) {
		return status.equals("Pending") || status.equals("Processing") || status.equals("Shipped")
				|| status.equals("Delivered") || status.equals("Cancelled");
	}

	public boolean isUserPresent(UserModel model) {
		try {
			String query = "select count(*) from users where user_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, model.getUserId());
			rs = stmt.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				return true; // User exists
			}
		} catch (Exception e) {
			logger.error("error in isUserPresent method ", e);
		}
		return false;
	}

}
