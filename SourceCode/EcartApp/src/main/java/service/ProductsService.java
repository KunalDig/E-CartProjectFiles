package service;

import model.ProductsModel;

public interface ProductsService {
	public boolean addProduct(ProductsModel model);

	public boolean updateProduct(ProductsModel model, String fieldToUpdate, Object newValue);

	public boolean deleteProduct(ProductsModel model);

	public void viewStock(ProductsModel model);

	public void displayAllProducts();

	public void viewAllOrders();
}
