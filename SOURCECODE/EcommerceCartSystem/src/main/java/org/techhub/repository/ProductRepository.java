package org.techhub.repository;

import org.techhub.model.OrdersModel;
import org.techhub.model.ProductModel;
import java.util.*;

public interface ProductRepository {
	public boolean addNewProduct(ProductModel prod);

	public Optional<List<ProductModel>> getAllProducts();

	public ProductModel getProductByName(String prodName);

	public boolean updateProduct(ProductModel model, String fieldToUpdate, Object newValue);

	public boolean deleteProduct(ProductModel model);

	public void viewStock(ProductModel model);

	public void viewAllOrders();

	public boolean updateOrderStatus(OrdersModel model, String newStatus);
}
