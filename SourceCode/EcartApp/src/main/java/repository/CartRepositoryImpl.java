package repository;

import java.sql.PreparedStatement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import model.ProductsModel;
import model.UsersModel;

public class CartRepositoryImpl extends DBSTATE implements CartRepository {
	private static Logger logger = Logger.getLogger(DBConfig.class);
	static {
		PropertyConfigurator.configure(
				"F:\\Project\\E-Comerce Cart System\\SourceCode\\EcartApp\\src\\main\\resources\\application.properties");

	}

	public boolean isProductPresentById(ProductsModel model) {
		try {
			logger.info("isProductPresentById method is called");
			String query = "SELECT COUNT(*) FROM products WHERE product_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, model.getProduct_id());
			return rs.next() && rs.getInt(1) > 0;
		} catch (Exception e) {
			logger.error("Error in isProductPresentById method, ", e);
			return false;
		}

	}

	public boolean isUserPresentById(UsersModel model) {
		try {
			logger.info("isUserPresentById method is called");
			String query = "SELECT COUNT(*) FROM users WHERE user_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, model.getUserId());
			rs = stmt.executeQuery();
			return rs.next() && rs.getInt(1) > 0;
		} catch (Exception e) {
			logger.error("Error in isUserPresentById method, ", e);
			return false;
		}

	}

	public boolean isStockAvailable(ProductsModel model, int quantity) {
		try {
			logger.info("isStockAvailable method is called");
			String query = "SELECT stock_quantity FROM products WHERE product_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, model.getProduct_id());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("stock_quantity") >= quantity;
			}
		} catch (Exception e) {
			logger.error("Error in isStockAvailable method, ", e);
			return false;
		}
		return false;

	}

	public void updateProductStock(ProductsModel model, int quantity) {
		try {
			logger.info("updateProductStock method is called");
			String checkStockQuery = "SELECT stock_quantity FROM products WHERE product_id = ?";
			String updateQuery = "UPDATE products SET stock_quantity = stock_quantity - ? WHERE product_id = ?";
			PreparedStatement checkStmt = conn.prepareStatement(checkStockQuery);
			checkStmt.setInt(1, model.getProduct_id());
			rs = checkStmt.executeQuery();
			if (rs.next()) {
				int currentStock = rs.getInt("stock_quantity");
				if (currentStock < quantity) {
					System.out.println("Insufficient stock for product ID: " + model.getProduct_id());
					return;
				}
			} else {
				System.out.println("Product with ID " + model.getProduct_id() + " does not exist.");
				return;
			}

			stmt = conn.prepareStatement(updateQuery);
			stmt.setInt(1, quantity);
			stmt.setInt(2, model.getProduct_id());
			int rowsUpdated = stmt.executeUpdate();

			if (rowsUpdated > 0) {
				System.out.println("Stock updated successfully for product ID: " + model.getProduct_id());
			} else {
				System.out.println("Failed to update stock for product ID: " + model.getProduct_id());
			}
		} catch (Exception e) {
			logger.error("Error in updateProductStock method, ", e);
		}

	}

	public boolean addProductToCart(UsersModel userModel, ProductsModel productModel, int quantity) {
		try {
			logger.info("addProductToCart method is called");
			if (!isProductPresentById(productModel)) {
				System.out.println("Product with ID " + productModel.getProduct_id() + " does not exist.");
				return false;
			}
			

	        // Check if the user exists
	        if (!isUserPresentById(userModel)) {
	            System.out.println("User with ID " + userModel.getUserId() + " does not exist.");
	            return false;
	        }
	        
	        // Check if sufficient stock is available
	        if (!isStockAvailable(productModel, quantity)) {
	            System.out.println("Insufficient stock for product ID " + productModel.getProduct_id());
	            return false;
	        }
	        
	     // Add product to cart or update quantity if it already exists
	        String insertOrUpdateQuery = " INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = quantity + ?";
	        stmt = conn.prepareStatement(insertOrUpdateQuery);
	        stmt.setInt(1, userModel.getUserId());
            stmt.setInt(2, productModel.getProduct_id());
            stmt.setInt(3, quantity);
            stmt.setInt(4, quantity);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Deduct stock after adding to cart
                updateProductStock(productModel, quantity);
//                System.out.println("Product added to cart successfully!");
                return true;
            }
		} catch (Exception e) {
			logger.error("Error in addProductToCart method, ", e);
		}
		return false;
	}
}
