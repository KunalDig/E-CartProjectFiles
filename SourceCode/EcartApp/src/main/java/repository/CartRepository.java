package repository;

import model.ProductsModel;
import model.UsersModel;

public interface CartRepository {
	public boolean isProductPresentById(ProductsModel model);
	public boolean isUserPresentById(UsersModel model);
	public boolean isStockAvailable(ProductsModel model, int quantity);
	public void updateProductStock(ProductsModel model, int quantity);
	public boolean addProductToCart(UsersModel userModel, ProductsModel productModel, int quantity); 
}
