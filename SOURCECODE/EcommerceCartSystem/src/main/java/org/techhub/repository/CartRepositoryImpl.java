package org.techhub.repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.techhub.model.CartModel;
import org.techhub.model.ProductModel;
import org.techhub.model.UserModel;

public class CartRepositoryImpl extends DBSTATE implements CartRepository {
	private static Logger logger = Logger.getLogger(CartRepositoryImpl.class);
	static {
		PropertyConfigurator.configure(
				"C:\\Users\\abcd\\Desktop\\Project\\EcommerceCartSystem\\src\\main\\resources\\application.properties");
	}

	@Override
	public boolean addProductToCart(int userId, ProductModel product, int quantity) {
		try {
			// checking if the product already exists in the cart
			stmt = conn.prepareStatement("select quantity from cart where user_id = ? and product_id = ?");
			stmt.setInt(1, userId);
			stmt.setInt(2, product.getProductId());
			rs = stmt.executeQuery();

			if (rs.next()) {
				// If product already exists, we will update the quantity
				int existingQuantity = rs.getInt("quantity");
				int newQuantity = existingQuantity + quantity;

				stmt = conn.prepareStatement("update cart set quantity = ? where user_id = ? and product_id = ?");
				stmt.setInt(1, newQuantity);
				stmt.setInt(2, userId);
				stmt.setInt(3, product.getProductId());

				int result = stmt.executeUpdate();
				if (result > 0) {
					logger.info("Product quantity updated in cart: " + product.getName());
					return true;
				} else {
					logger.warn("Failed to update product quantity in cart.");
				}
			} else {
				// If product doesn't exist, inserting it as a new entry
				stmt = conn.prepareStatement(
						"insert into cart (user_id, product_id, quantity, added_at) VALUES (?, ?, ?, ?)");
				stmt.setInt(1, userId);
				stmt.setInt(2, product.getProductId());
				stmt.setInt(3, quantity);
				stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

				int result = stmt.executeUpdate();
				if (result > 0) {
					logger.info("Product added to cart: " + product.getName());
					return true;
				} else {
					logger.warn("Failed to add product to cart.");
				}
			}
		} catch (SQLException e) {
			logger.error("Error adding product to cart: " + e.getMessage(), e);
		}
		return false;
	}

	public boolean updateProductStock(int productId, int newQuantity) {
		try {
			stmt = conn.prepareStatement("update products set stock_quantity = ? where product_id = ?");
			stmt.setInt(1, newQuantity);
			stmt.setInt(2, productId);

			int result = stmt.executeUpdate();
			if (result > 0) {
				logger.info("Product Stock Updated Successfully.");
				return true;
			} else {
				logger.warn("Failed to Update Stock.");
				return false;
			}
		} catch (SQLException e) {
			logger.error("Error updating product stock" + e.getMessage());
			return false;
		}

	}

	@Override
	public ProductModel getProductById(int productId) {
		logger.info("Fetching product with ID: " + productId);
		try {
			logger.debug("Getting product with id: " + productId);
			stmt = conn.prepareStatement("select * from products where product_id = ?");
			stmt.setInt(1, productId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				logger.info("Product found: " + rs.getString("name"));
				return new ProductModel(rs.getInt("product_id"), rs.getString("name"), rs.getString("description"),
						rs.getDouble("price"), rs.getInt("stock_quantity"),
						rs.getTimestamp("created_at").toLocalDateTime());
			} else {
				logger.warn("No product found for Id: " + productId);
				return null;
			}
		} catch (Exception e) {
			logger.error("Error getting product by Id: " + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public CartModel getCartItems(UserModel user) {
		try {
			stmt = conn.prepareStatement("select * from cart where user_id = ?");
			stmt.setInt(1, user.getUserId());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return new CartModel(rs.getInt("cart_id"), rs.getInt("user_id"), rs.getInt("product_id"),
						rs.getInt("quantity"), rs.getTimestamp("added_at").toLocalDateTime());
			} else {
				return null;
			}
		} catch (SQLException e) {
			logger.error("");
		}
		return null;
	}

	@Override
	public boolean removeProductFromCart(int userId, ProductModel product, int quantity) {
		try {
			// checking if the product exists in the cart for the given user
			stmt = conn.prepareStatement("select quantity from cart where user_id = ? and product_id = ?");
			stmt.setInt(1, userId);
			stmt.setInt(2, product.getProductId());
			rs = stmt.executeQuery();

			if (rs.next()) {
				int existingQuantity = rs.getInt("quantity");

				// if the quantity to be removed is less than or equal to the current quantity
				// in the cart
				if (quantity <= existingQuantity) {
					int newQuantity = existingQuantity - quantity;

					if (newQuantity > 0) {
						// update the quantity of the product in the cart
						stmt = conn
								.prepareStatement("update cart set quantity = ? where user_id = ? and product_id = ?");
						stmt.setInt(1, newQuantity);
						stmt.setInt(2, userId);
						stmt.setInt(3, product.getProductId());
						int result = stmt.executeUpdate();

						if (result > 0) {
							logger.info("Product quantity updated in cart: " + product.getName());
							// Update product stock
							int newStock = product.getStockQuantity() + quantity;
							stmt = conn.prepareStatement("update products set stock_quantity = ? where product_id = ?");
							stmt.setInt(1, newStock);
							stmt.setInt(2, product.getProductId());
							stmt.executeUpdate();
							return true;
						}

					} else {
						// If the quantity becomes zero, remove the product from the cart
						stmt = conn.prepareStatement("delete from cart where user_id = ? and product_id = ?");
						stmt.setInt(1, userId);
						stmt.setInt(2, product.getProductId());
						int result = stmt.executeUpdate();
						if (result > 0) {
							logger.info("Product removed from cart: " + product.getName());
							// Update product stock
							int newStock = product.getStockQuantity() + existingQuantity; // Return the entire quantity
							stmt = conn.prepareStatement("update products set stock_quantity = ? where product_id = ?");
							stmt.setInt(1, newStock);
							stmt.setInt(2, product.getProductId());
							stmt.executeUpdate();
							return true;
						}
					}
				} else {
					logger.warn("Insufficient quantity in cart to remove.");
				}
			} else {
				logger.warn("Product not found in the cart.");
			}
		} catch (SQLException e) {
			logger.error("Error removing product from cart: " + e.getMessage(), e);
		}
		return false;
	}

	@Override
	public List<CartModel> getCartByUserId(int userId) {
		List<CartModel> cartList = new ArrayList<>();
		try {
			stmt = conn.prepareStatement("select * from cart where user_id = ?");
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				CartModel cart = new CartModel(rs.getInt("cart_id"), rs.getInt("user_id"), rs.getInt("product_id"),
						rs.getInt("quantity"), rs.getTimestamp("added_at").toLocalDateTime());
				cartList.add(cart);
			}
		} catch (SQLException e) {
			logger.error("Error fetching cart data: " + e.getMessage());
		}
		return cartList;
	}

	@Override
	public void clearCart(int userId) {
		try {
			stmt = conn.prepareStatement("DELETE FROM cart WHERE user_id = ?");
			stmt.setInt(1, userId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Failed to clear cart: " + e.getMessage(), e);
		}

	}

}
