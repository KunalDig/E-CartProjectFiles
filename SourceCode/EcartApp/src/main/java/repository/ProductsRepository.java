package repository;

import model.ProductsModel;

public interface ProductsRepository {
	public boolean addProduct(ProductsModel model);

	public boolean isValidField(String field);

	public boolean updateProduct(ProductsModel model, String fieldToUpdate, Object newValue);

	public boolean isProductPresent(ProductsModel model);

	public boolean deleteProduct(ProductsModel model);

	public void viewStock(ProductsModel model);

	public void displayAllProducts();
	
	public void viewAllOrders();
}
