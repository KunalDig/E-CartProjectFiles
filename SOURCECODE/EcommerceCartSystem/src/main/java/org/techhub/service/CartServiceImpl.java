package org.techhub.service;

import java.util.List;

import org.techhub.model.CartModel;
import org.techhub.model.ProductModel;
import org.techhub.model.UserModel;
import org.techhub.repository.CartRepository;
import org.techhub.repository.CartRepositoryImpl;

public class CartServiceImpl implements CartService {
	CartRepository crepo = new CartRepositoryImpl();

	@Override
	public boolean addProductToCart(int userId, ProductModel product, int quantity) {
		return crepo.addProductToCart(userId, product, quantity);
	}

	@Override
	public boolean updateProductStock(int productId, int newStockQuantity) {
		return crepo.updateProductStock(productId, newStockQuantity);
	}

	@Override
	public ProductModel getProductById(int productId) {
		return crepo.getProductById(productId);
	}

	@Override
	public CartModel getCartItems(UserModel user) {
		return crepo.getCartItems(user);
	}

	@Override
	public boolean removeProductFromCart(int userId, ProductModel product, int quantity) {
		return crepo.removeProductFromCart(userId, product, quantity);
	}

	public List<CartModel> getCartByUserId(int userId) {
		return crepo.getCartByUserId(userId);
	}

	@Override
	public void clearCart(int userId) {
		crepo.clearCart(userId);

	}

}
