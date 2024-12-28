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
public class CartMdel {

	private int cart_id;
	private int user_id;
	private int product_id;
	private int quantity;
	private Timestamp added_at;
}
