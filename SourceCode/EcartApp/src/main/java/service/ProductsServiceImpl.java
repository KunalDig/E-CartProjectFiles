package service;

import model.ProductsModel;
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

}
