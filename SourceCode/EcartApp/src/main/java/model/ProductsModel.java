package model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductsModel {
	private int product_id;
	private String name;
	private String description;
	private double price;
	private int stock_quantity;
	private Timestamp createdAt;
}
