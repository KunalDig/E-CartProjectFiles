package service;

import model.OrdersModel;
import model.ProductsModel;
import model.UsersModel;
import repository.ProductsRepository;
import repository.ProductsRepositoryImpl;

public class ProductsServiceImpl implements ProductsService {
	ProductsRepository productsRepository = new ProductsRepositoryImpl();
	public boolean addProduct(ProductsModel model) {
		return productsRepository.addProduct(model);
	}
	public boolean updateProduct(ProductsModel model, String fieldToUpdate, Object newValue) {
		
		return productsRepository.updateProduct(model, fieldToUpdate, newValue);
	}
	public boolean deleteProduct(ProductsModel model) {
		
		return productsRepository.deleteProduct(model);
	}
	public void viewStock(ProductsModel model) {
		productsRepository.viewStock(model);
		
	}
	public void displayAllProducts() {
		productsRepository.displayAllProducts();
		
	}
	public void viewAllOrders() {
		productsRepository.viewAllOrders();
		
	}
	public boolean updateOrderStatus(OrdersModel model, String newStatus) {
		return productsRepository.updateOrderStatus(model, newStatus);
	}
	public void viewUserTransactionHistory(UsersModel model) {
		productsRepository.viewUserTransactionHistory(model);
		
	}
	public void viewProductStockByName(ProductsModel model) {
		productsRepository.viewProductStockByName(model);
		
	}

}
