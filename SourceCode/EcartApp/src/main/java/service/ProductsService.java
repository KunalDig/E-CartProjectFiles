package service;

import model.OrdersModel;
import model.ProductsModel;
import model.UsersModel;

public interface ProductsService {
	public boolean addProduct(ProductsModel model);

	public boolean updateProduct(ProductsModel model, String fieldToUpdate, Object newValue);

	public boolean deleteProduct(ProductsModel model);

	public void viewStock(ProductsModel model);

	public void displayAllProducts();

	public void viewAllOrders();
	
	public boolean updateOrderStatus(OrdersModel model, String newStatus);
	
	public void viewUserTransactionHistory(UsersModel model);
	
	public void viewProductStockByName(ProductsModel model);
}
