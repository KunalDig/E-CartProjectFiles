package org.techhub.service;

import java.util.*;

import org.techhub.model.OrdersModel;
import org.techhub.model.ProductModel;
import org.techhub.repository.ProductRepository;
import org.techhub.repository.ProductRepositoryImpl;

public class ProductServiceImpl implements ProductService {
	ProductRepository prepo = new ProductRepositoryImpl();

	public boolean addNewProduct(ProductModel prod) {
		return prepo.addNewProduct(prod);
	}

	public Optional<List<ProductModel>> getAllProducts() {
		return prepo.getAllProducts();
	}

	public ProductModel getProductByName(String prodName) {
		return prepo.getProductByName(prodName);
	}

	public boolean updateProduct(ProductModel model, String fieldToUpdate, Object newValue) {
		return prepo.updateProduct(model, fieldToUpdate, newValue);
	}

	public boolean deleteProduct(ProductModel model) {
		return prepo.deleteProduct(model);
	}

	public void viewStock(ProductModel model) {
		prepo.viewStock(model);
	}

	@Override
	public void viewAllOrders() {
		prepo.viewAllOrders();
	}

	@Override
	public boolean updateOrderStatus(OrdersModel model, String newStatus) {
		// TODO Auto-generated method stub
		return prepo.updateOrderStatus(model, newStatus);
	}
}
