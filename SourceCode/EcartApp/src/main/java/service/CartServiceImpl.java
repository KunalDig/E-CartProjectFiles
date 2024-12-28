package service;

import model.ProductsModel;
import model.UsersModel;
import repository.CartRepository;
import repository.CartRepositoryImpl;

public class CartServiceImpl implements CartService {
  CartRepository cartRepository = new CartRepositoryImpl();
	public boolean addProductToCart(UsersModel userModel, ProductsModel productModel, int quantity) {
		return cartRepository.addProductToCart(userModel, productModel, quantity);
	}

}
