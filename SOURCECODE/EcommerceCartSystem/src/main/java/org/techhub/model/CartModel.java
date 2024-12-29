package org.techhub.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartModel {

    private int cartId;
    private int userId;
    private int productId;
    private int quantity;
    private LocalDateTime addedAt;
}