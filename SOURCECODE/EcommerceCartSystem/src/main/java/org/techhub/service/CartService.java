package org.techhub.service;

import java.util.List;

import org.techhub.model.CartModel;
import org.techhub.model.ProductModel;
import org.techhub.model.UserModel;

public interface CartService {
	public boolean addProductToCart(int userId, ProductModel product, int quantity);

	public boolean updateProductStock(int productId, int newStockQuantity);

	public ProductModel getProductById(int productId);

	public CartModel getCartItems(UserModel user);

	public boolean removeProductFromCart(int userId, ProductModel product, int quantity);

	public List<CartModel> getCartByUserId(int userId);

	public void clearCart(int userId);
}
