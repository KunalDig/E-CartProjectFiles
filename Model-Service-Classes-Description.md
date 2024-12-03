# Model Classes

## Product

~~~
Description: Represents items available for purchase.
Attributes:
id: Unique identifier for the product.
name: Name of the product.
description: Detailed information about the product.
price: Price per unit.
stock: Number of items available.
imageUrl: URL of the product image.
category: Category the product belongs to.
~~~

## User

~~~
Description: Represents a customer on the platform.
Attributes:
id: Unique identifier for the user.
username: User's account name.
email: User's email address.
password: Encrypted password.
address: Shipping address of the user.
~~~

## CartItem

~~~
Description: Represents an item in a user's cart.
Attributes:
id: Unique identifier for the cart item.
userId: ID of the user who owns the cart.
productId: ID of the product in the cart.
quantity: Number of units of the product.
subtotal: Price calculated as quantity * product.price.
~~~

## Order

~~~
Description: Represents a completed purchase.
Attributes:
id: Unique identifier for the order.
userId: ID of the user who placed the order.
orderDate: Date when the order was placed.
totalPrice: Total amount paid for the order.
status: Current status of the order (e.g., "Processing", "Shipped", "Delivered").
~~~

## OrderItem

~~~
Description: Represents individual items within an order.
Attributes:
id: Unique identifier for the order item.
orderId: ID of the associated order.
productId: ID of the purchased product.
quantity: Number of units purchased.
price: Price per unit of the product at the time of purchase.
~~~


# Service Classes

## ProductService

~~~
Description: Handles operations related to products.
Responsibilities:
Retrieve product details.
Search and filter products.
Check stock availability.
~~~

## UserService

~~~
Description: Manages user information and authentication.
Responsibilities:
Register new users.
Authenticate users.
Retrieve user details.

~~~
## CartService

~~~
Description: Manages cart operations for users.
Responsibilities:
Add, update, or remove items in the cart.
Calculate the total price.
Retrieve the cart's current state.
~~~

## OrderService

~~~
Description: Handles order placement and history.
Responsibilities:
Create new orders.
Retrieve order history for a user.
Update order status.
~~~

## PaymentService

~~~
Description: Manages payment processing.
Responsibilities:
Integrate with payment gateways.
Validate and confirm payments.
Handle payment errors or refunds.
~~~

## NotificationService (Optional)

~~~
Description: Sends notifications to users.
Responsibilities:
Notify users about order status updates.
Remind users of abandoned carts.
Promote offers and discounts.
~~~

## AnalyticsService (Optional)

~~~
Description: Tracks and analyzes user behavior.
Responsibilities:
Monitor frequently viewed or purchased products.
Provide sales insights.
Track cart abandonment rates.
~~~