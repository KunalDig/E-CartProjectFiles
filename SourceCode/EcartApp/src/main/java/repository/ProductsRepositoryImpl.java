package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import model.ProductsModel;

public class ProductsRepositoryImpl extends DBSTATE implements ProductsRepository {
	private static Logger logger = Logger.getLogger(DBConfig.class);
	static {
		PropertyConfigurator.configure(
				"F:\\Project\\E-Comerce Cart System\\SourceCode\\EcartApp\\src\\main\\resources\\application.properties");

	}

	public boolean addProduct(ProductsModel model) {
		logger.info("Add product method is called.");
		PreparedStatement cstmt = null;
		try {
			// Validate inputs
			if (model.getName() == null || model.getName().isEmpty() || model.getDescription() == null
					|| model.getDescription().isEmpty() || model.getPrice() <= 0 || model.getStock_quantity() < 0) {
				System.out.println("Invalid input values. Please check the product details.");
				return false;
			}
			// SQL query to check if the product name is unique
			String checkQuery = "SELECT COUNT(*) FROM products WHERE name = ?";
			// SQL query to insert the product into the database
			String insertQuery = "INSERT INTO products (name, description, price, stock_quantity) VALUES (?, ?, ?, ?)";
			cstmt = conn.prepareStatement(checkQuery);
			cstmt.setString(1, model.getName());
			rs = cstmt.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				System.out.println("Product name already exists. Please use a different name.");
				return false;
			}

			stmt = conn.prepareStatement(insertQuery);
			stmt.setString(1, model.getName());
			stmt.setString(2, model.getDescription());
			stmt.setDouble(3, model.getPrice());
			stmt.setInt(4, model.getStock_quantity());

			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				logger.info("New product added.");
				System.out.println("Product added successfully!");
				return true;
			}
		} catch (Exception e) {
			logger.error("Error while adding product.", e);

		}
		return false;
	}

	public boolean updateProduct(ProductsModel model, String fieldToUpdate, Object newValue) {
		try {
			logger.info("Update product method is called.");
			// Validate field name
			if (!isValidField(fieldToUpdate)) {
				System.out.println("Invalid field name. Please choose a valid field.");
				return false;
			}
			if (!isProductPresent(model)) {
				System.out.println("Product with ID " + model.getProduct_id() + " does not exist.");
				return false;
			}
			String updateQuery = "update products set " + fieldToUpdate + " = ? where product_id = ?";
			stmt = conn.prepareStatement(updateQuery);
			stmt.setObject(1, newValue); // `setObject` allows flexibility for different data types
			stmt.setInt(2, model.getProduct_id());
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated > 0) {
//				System.out.println("Product updated successfully!");
				return true;
			} else {
				logger.info("Product cannot be updated ");
				return false;
			}

		} catch (Exception e) {
			logger.info("Product cannot be updated " + e);
			return false;
		}

	}

	public boolean deleteProduct(ProductsModel model) {
		try {
			logger.info("delete product method is called.");
			// Check if the product exists
			if (!isProductPresent(model)) {
				System.out.println("Product with ID " + model.getProduct_id() + " does not exist.");
				return false;
			}
			// SQL query to delete the product
			String deleteQuery = "delete from products where product_id = ?";
			stmt = conn.prepareStatement(deleteQuery);
			stmt.setInt(1, model.getProduct_id());
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

	public void viewStock(ProductsModel model) {
		try {
			logger.info("view stock method is called.");
			// Check if the product exists
			if (!isProductPresent(model)) {
				System.out.println("Product with ID " + model.getProduct_id() + " does not exist.");
				return;
			}
			String query = "select name, stock_quantity from products where product_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, model.getProduct_id());
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

	public void displayAllProducts() {
		try {
			String query = "select product_id, name, description, price, stock_quantity from products";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			System.out.printf("%-10s %-20s %-30s %-10s %-15s%n", "Product ID", "Name", "Description", "Price",
					"Stock Quantity");
			System.out.println(
					"----------------------------------------------------------------------------------------");
			while (rs.next()) {
				int productId = rs.getInt("product_id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				double price = rs.getDouble("price");
				int stockQuantity = rs.getInt("stock_quantity");

				System.out.printf("%-10d %-20s %-30s %-10.2f %-15d%n", productId, name, description, price,
						stockQuantity);
			}
		} catch (Exception e) {
			logger.error("cannot view product details." + e);
		}

	}

	public void viewAllOrders() {
		try {
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

				PreparedStatement itemStmt = conn.prepareStatement(itemQuery);
				itemStmt.setInt(1, orderId);
				ResultSet itemRs = itemStmt.executeQuery();
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

	public boolean isProductPresent(ProductsModel model) {

		try {
			String query = "SELECT COUNT(*) FROM products WHERE product_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, model.getProduct_id());
			rs = stmt.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.info("some error appears during product search.");
		}
		return false;
	}

}
