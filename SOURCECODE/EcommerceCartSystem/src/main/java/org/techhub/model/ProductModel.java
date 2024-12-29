package org.techhub.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductModel {

	private int productId;
	private String name;
	private String description;
	private double price;
	private int stockQuantity;
	private LocalDateTime createdAt;

//	public ProductModel() {
//		this.createdAt = LocalDateTime.now();
//	}

//	public ProductModel(int productId, String name, String description, double price, int stockQuantity,
//			LocalDateTime createdAt) {
//		super();
//		this.productId = productId;
//		this.name = name;
//		this.description = description;
//		this.price = price;
//		this.stockQuantity = stockQuantity;
//		this.createdAt = createdAt;
//	}


//	public String toString() {
//		return "[" + productId + "," + name + "," + description + "," + price + "," + stockQuantity + "," + createdAt
//				+ "]";
//	}

//	public boolean equals(Object obj) {
//		ProductModel prod = (ProductModel) obj;
//
//		if (prod.getProductId() == this.productId && prod.getName().equals(this.name)
//				&& prod.getDescription().equals(this.description) && prod.getPrice() == this.price
//				&& prod.getStockQuantity() == this.stockQuantity && prod.getCreatedAt() == this.createdAt) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	public int hashCode() {
//		return productId * 10000;
//	}
}
