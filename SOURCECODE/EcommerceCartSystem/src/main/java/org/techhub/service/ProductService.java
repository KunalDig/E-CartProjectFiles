package org.techhub.service;

import java.util.*;

import org.techhub.model.OrdersModel;
import org.techhub.model.ProductModel;

public interface ProductService {
	public boolean addNewProduct(ProductModel prod);

	public Optional<List<ProductModel>> getAllProducts();

	public ProductModel getProductByName(String prodName);

	public boolean updateProduct(ProductModel model, String fieldToUpdate, Object newValue);

	public boolean deleteProduct(ProductModel model);

	public void viewStock(ProductModel model);

	public void viewAllOrders();

	public boolean updateOrderStatus(OrdersModel model, String newStatus);
}
