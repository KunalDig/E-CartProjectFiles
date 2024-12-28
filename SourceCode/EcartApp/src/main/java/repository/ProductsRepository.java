package repository;

import model.OrdersModel;
import model.ProductsModel;
import model.UsersModel;

public interface ProductsRepository {
	public boolean addProduct(ProductsModel model);

	public boolean isValidField(String field);

	public boolean updateProduct(ProductsModel model, String fieldToUpdate, Object newValue);

	public boolean isProductPresent(ProductsModel model);

	public boolean deleteProduct(ProductsModel model);

	public void viewStock(ProductsModel model);

	public void displayAllProducts();
	
	public void viewAllOrders();
	
	public boolean isOrderPresent(OrdersModel model);
	
	public boolean isValidOrderStatus(String status);
	
	public boolean updateOrderStatus(OrdersModel model, String newStatus);
	
	public boolean isUserPresent(UsersModel model);
	
	public void viewUserTransactionHistory(UsersModel model);
	
	public boolean isProductPresentByName(ProductsModel model);
	
	public void viewProductStockByName(ProductsModel model);
	
}
