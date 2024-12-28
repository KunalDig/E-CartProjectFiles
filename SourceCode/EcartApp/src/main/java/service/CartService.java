package service;

import model.ProductsModel;
import model.UsersModel;

public interface CartService {
	public boolean addProductToCart(UsersModel userModel, ProductsModel productModel, int quantity);
}
