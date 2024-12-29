package org.techhub.client;

import java.time.LocalDateTime;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.techhub.model.CartModel;
import org.techhub.model.OrderItemModel;
import org.techhub.model.OrdersModel;
import org.techhub.model.ProductModel;
import org.techhub.model.UserModel;
import org.techhub.service.AuthService;
import org.techhub.service.AuthServiceImpl;
import org.techhub.service.CartService;
import org.techhub.service.CartServiceImpl;
import org.techhub.service.OrdersService;
import org.techhub.service.OrdersServiceImpl;
import org.techhub.service.ProductService;
import org.techhub.service.ProductServiceImpl;

public class EcommerceCartSystemApp {

	private static Logger logger = Logger.getLogger(EcommerceCartSystemApp.class);

	static {
		PropertyConfigurator.configure(
				"C:\\Users\\abcd\\Desktop\\Project\\EcommerceCartSystem\\src\\main\\resources\\application.properties");
	}

	public static void main(String[] args) {
		logger.info("Application Started...");
		Scanner sc = new Scanner(System.in);
		ProductService prodService = new ProductServiceImpl();
		CartService cartService = new CartServiceImpl();
		AuthService authService = new AuthServiceImpl();
		Map<Integer, Integer> cartItems = new HashMap<Integer, Integer>();
		double totalPrice = 0;
		boolean flag = true;

		do {
			logger.debug("Displaying main menu...");
			System.out.println("1. Register as admin or normal user(user)\n2. Login as admin or user\n3. Exit");
			System.out.println("Enter your Choice: ");
			int ch = sc.nextInt();

			switch (ch) {
			case 1:
				// Register as admin or normal user(user)
				logger.info("User selected to register.");
				sc.nextLine();
				System.out.println("Enter Username: ");
				String username = sc.nextLine();
				System.out.println("Enter Email: ");
				String email = sc.nextLine();
				System.out.println("Enter password: ");
				String password = sc.nextLine();
				System.out.println("Register as: 1. Admin 2. Normal User");
				int roleChoice = sc.nextInt();
				LocalDateTime userCreatedAt = LocalDateTime.now();
				String role = (roleChoice == 1) ? "admin" : "normal";

				boolean isRegistered = authService
						.registerUser(new UserModel(0, username, email, password, userCreatedAt, role));
				String result = isRegistered ? "Registration successful!"
						: "Registration failed. Username or email may already exist.";
				System.out.println(result);

				if (isRegistered) {
					logger.info("User registered successfully: " + username + ", Role: " + role);
				} else {
					logger.warn("Registration failed for user: " + username);
				}

				break;

			case 2:
				// Login as admin or user
				logger.info("User selected to log in.");
				sc.nextLine();
				System.out.println("Enter username: ");
				String loginUsername = sc.nextLine();

				System.out.println("Enter password: ");
				String loginPassword = sc.nextLine();

				Optional<UserModel> loggedInUser = authService.login(loginUsername, loginPassword);

				if (loggedInUser.isPresent()) {
					UserModel user = loggedInUser.get();
					logger.info("Login successful for user: " + loginUsername);
					if (user.getRole().equalsIgnoreCase("admin")) {
						System.out.println("Welcome Admin: " + user.getUsername());
						adminFunctionalities(sc, prodService);
					} else {
						System.out.println("Welcome User: " + user.getUsername());
						loadCartForUser(user.getUserId(), cartItems, cartService);
						userFunctionalities(sc, prodService, cartService, user, cartItems, totalPrice);
					}
				} else {
					logger.warn("Login failed for username: " + loginUsername);
					System.out.println("Invalid username or password.");
				}
				break;

			case 3:
				// Exit Application
				logger.info("User selected to exit the application.");
				System.out.println("Exiting Application....");
				flag = false;
				break;

			default:
				logger.warn("Invalid choice entered in main menu: " + ch);
				System.out.println("Invalid choice...");
				break;

			}

		} while (flag);
		logger.info("Application exited.");

	}

	private static void adminFunctionalities(Scanner sc, ProductService prodService) {
		logger.info("Admin functionalities started...");
		boolean flag = true;
		do {
			ProductModel productModel = new ProductModel();
			OrdersModel ordersModel = new OrdersModel();
			System.out.println(
					"\nAdmin Menu: \n1: Add new Product\n2: View all Products\n3: Search Product\n4: Update Product\n5: Delete Product\n6: View Stock\n7: View All orders\n8: Update Order Status\n9: Logout");
			System.out.println("Enter your choice: ");
			int ch = sc.nextInt();

			switch (ch) {
			case 1:// Add new Product
				logger.debug("Admin selected to add a new product.");
				System.out.println("Enter Product name: ");
				sc.nextLine();
				String prodName = sc.nextLine();
				System.out.println("Enter Product Description");
				String prodDesc = sc.nextLine();
				System.out.println("Enter Product Price");
				double prodPrice = sc.nextDouble();
				System.out.println("Enter Stock Quantity");
				int prodStockQuantity = sc.nextInt();
				LocalDateTime prodCreatedAt = LocalDateTime.now();

				boolean isAdded = prodService.addNewProduct(
						new ProductModel(0, prodName, prodDesc, prodPrice, prodStockQuantity, prodCreatedAt));

				String result = isAdded ? "Product Added Successfully" : "Product Not Added";

				System.out.println(result);

				if (isAdded) {
					logger.info("Product added successfully: " + prodName);
				} else {
					logger.warn("Failed to add product: " + prodName);
				}
				break;

			case 2:// View all Products
				logger.debug("Admin selected to view all products.");
				Optional<List<ProductModel>> olist = prodService.getAllProducts();
				if (olist.isPresent()) {
					List<ProductModel> list = olist.get();
					list.forEach(
							(p) -> System.out.println(p.getProductId() + "\t" + p.getName() + "\t" + p.getDescription()
									+ "\t" + p.getPrice() + "\t" + p.getStockQuantity() + "\t" + p.getCreatedAt()));
					logger.info("Retrieved " + list.size() + " products from the database.");
				} else {
					logger.info("No product data available.");
					System.out.println("There is no Product data Available");
				}
				break;

			case 3:// Search Product
				logger.debug("Admin selected to search for a product.");
				System.out.println("Enter Product Name: ");
				sc.nextLine();
				prodName = sc.nextLine();
				// System.out.println(prodName);
				ProductModel p = prodService.getProductByName(prodName);

				if (p != null) {
					System.out.println(p.getProductId() + "\t" + p.getName() + "\t" + p.getDescription() + "\t"
							+ p.getPrice() + "\t" + p.getStockQuantity() + "\t" + p.getCreatedAt());
					logger.info("Product found: " + prodName);
				} else {
					System.out.println("Product Not Found!");
					logger.warn("Product not found: " + prodName);
				}
				break;

			case 4:// Update Product
				sc.nextLine();
				System.out.println("Enter product ID : ");
				int productId = sc.nextInt();
				sc.nextLine();
				productModel.setProductId(productId);

				System.out.println("Enter the field to update (name, description, price, stock_quantity):");
				String fieldToUpdate = sc.nextLine();

				System.out.println("Enter the new value:");
				Object newValue = null;
				if (fieldToUpdate.equalsIgnoreCase("name")) {
					newValue = sc.nextLine();
				} else if (fieldToUpdate.equalsIgnoreCase("description")) {
					newValue = sc.nextLine();
				} else if (fieldToUpdate.equalsIgnoreCase("price")) {
					newValue = sc.nextDouble();
				} else if (fieldToUpdate.equalsIgnoreCase("stock_quantity")) {
					newValue = sc.nextInt();
				} else {
					System.out.println("Wrong field");
				}

				boolean b = prodService.updateProduct(productModel, fieldToUpdate, newValue);
				if (b) {
					System.out.println("Product updated succesfully.");
				} else {
					System.out.println("Product is not updated.");
				}
				break;

			case 5:// Delete Product
				sc.nextLine();
				System.out.println("Enter ID of product to be deleted : ");
				int productIdToDelete = sc.nextInt();
				productModel.setProductId(productIdToDelete);

				b = prodService.deleteProduct(productModel);
				if (b) {
					System.out.println("Product removed succesfully.");
				} else {
					System.out.println("Product could not be removed.");
				}
				break;

			case 6:// View Stock
				sc.nextLine();
				System.out.println("Enter ID of product whose stock you want to view : ");
				int productIdForStock = sc.nextInt();
				productModel.setProductId(productIdForStock);
				prodService.viewStock(productModel);
				break;

			case 7:// View All orders
				prodService.viewAllOrders();
				break;

			case 8:// Update Order Status
				sc.nextLine();
				System.out.println("Enter order id : ");
				int orderId = sc.nextInt();
				sc.nextLine();
				ordersModel.setOrder_id(orderId);

				System.out.println("Enter new status (Pending, Processing, Shipped, Delivered, Cancelled.): ");
				String status = sc.nextLine();

				b = prodService.updateOrderStatus(ordersModel, status);
				if (b) {
					System.out.println("Order status updated successfuly.");
				} else {
					System.out.println("Order status could not be updated.");
				}
				break;

			case 9:
				logger.info("Admin logged out.");
				System.out.println("Logged out as Admin.");
				flag = false;
				break;

			default:
				logger.warn("Invalid choice entered in admin menu: " + ch);
				System.out.println("Invalid choice...");
				break;
			}
		} while (flag);
	}

	private static void userFunctionalities(Scanner sc, ProductService prodService, CartService cartService,
			UserModel user, Map<Integer, Integer> cartItems, double totalPrice) {

		logger.info("User functionalities started...");
		boolean flag = true;

		do {
			System.out.println("\nUser Menu:");
			System.out.println("1: View all Products");
			System.out.println("2: Search Product");
			System.out.println("3: Logout");
			System.out.println("Enter your choice: ");
			int userChoice = sc.nextInt();

			switch (userChoice) {
			case 1:
				logger.debug("User selected to view all products.");
				Optional<List<ProductModel>> olist = prodService.getAllProducts();
				if (olist.isPresent()) {
					List<ProductModel> list = olist.get();
					logger.debug("Fetched " + list.size() + " products.");
					list.forEach(
							(p) -> System.out.println(p.getName() + "\t" + p.getDescription() + "\t" + p.getPrice()));
					logger.info("Retrieved " + list.size() + " products from the database.");
				} else {
					logger.info("No product data available.");
					System.out.println("There is no Product data Available");
				}
				break;

			case 2:
				logger.debug("User selected to search for a product.");
				sc.nextLine();
				System.out.println("Enter Product Name: ");
				String searchProdName = sc.nextLine();
				ProductModel p = prodService.getProductByName(searchProdName);

				if (p != null) {
					System.out.println(p.getName() + "\t" + p.getDescription() + "\t" + p.getPrice());
					logger.info("Product found: " + searchProdName);
					addProductToCart(sc, p, cartService, totalPrice, cartItems, user);
				} else {
					System.out.println("Product Not Found!");
					logger.warn("Product not found: " + searchProdName);
				}
				break;

			case 3:
				logger.info("User logged out.");
				System.out.println("Logged out as User.");
				flag = false;
				break;
			default:
				logger.warn("Invalid choice entered in user menu: " + userChoice);
				System.out.println("Invalid choice...");
				break;
			}
		} while (flag);
	}

	private static void addProductToCart(Scanner sc, ProductModel p, CartService cartService, double totalPrice,
			Map<Integer, Integer> cartItems, UserModel user) {
		OrdersService ordersService = new OrdersServiceImpl();
		boolean addFlag = true;

		do {
			System.out.println(
					"1. Add product to cart\n2. Remove Product from cart\n3. View Cart\n4. Proceed to Checkout\n5. Go Back");
			System.out.println("Enter your choice: ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Enter Quantity: ");
				int quantity = sc.nextInt();

				if (p.getStockQuantity() >= quantity) {
					if (cartItems.containsKey(p.getProductId())) {
						int existingQuantity = cartItems.get(p.getProductId());
						cartItems.put(p.getProductId(), existingQuantity + quantity);
						totalPrice = totalPrice + (p.getPrice() * quantity);
						System.out.println("Updated Product in Cart. Total Price: " + totalPrice);
						cartService.addProductToCart(user.getUserId(), p, quantity);
					} else {
						cartService.addProductToCart(user.getUserId(), p, quantity);
						cartItems.put(p.getProductId(), quantity);
						totalPrice = totalPrice + (p.getPrice() * quantity);

						System.out.println("Product Added to Cart. Total Price: " + totalPrice);
					}
					p.setStockQuantity(p.getStockQuantity() - quantity);
					cartService.updateProductStock(p.getProductId(), p.getStockQuantity());
				} else {
					System.out.println("Insufficient Stock! try reducing quantity");
				}
				break;
			case 2:
				System.out.println("Enter Quantity to Remove: ");
				quantity = sc.nextInt();

				if (cartItems.containsKey(p.getProductId())) {
					int existingQuantity = cartItems.get(p.getProductId());

					// check if the quantity to remove is valid or not
					if (existingQuantity >= quantity) {
						if (cartService.removeProductFromCart(user.getUserId(), p, quantity)) {
							cartItems.put(p.getProductId(), existingQuantity - quantity);
							totalPrice = totalPrice - (p.getPrice() * quantity);
							System.out.println("Product Removed from cart. Total Price: " + totalPrice);

							if (cartItems.get(p.getProductId()) == 0) {
								cartItems.remove(p.getProductId());
							}
						} else {
							System.out.println("Failed to remove product from cart.");
						}
					} else {
						System.out.println("You do not have that much quantity in your cart.");
					}

				} else {
					System.out.println("Product Not found in Cart.");
				}
				break;

			case 3:
				totalPrice = 0.0; // Reset total price
				if (!cartItems.isEmpty()) {
					System.out.println("Your Cart: ");
					for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
						int productId = entry.getKey();
						int cartQuantity = entry.getValue();

						ProductModel product = cartService.getProductById(productId);
						if (product != null) {
							double productTotalPrice = product.getPrice() * cartQuantity;
							totalPrice += productTotalPrice;
							System.out.println("Product: " + product.getName() + " Quantity: " + cartQuantity
									+ " Price: " + productTotalPrice);
						} else {
							System.out.println("Product Not found!");
						}
					}
					System.out.println("Total Cart Price: " + totalPrice);
				} else {
					System.out.println("Cart is empty.");
				}
				break;

			case 4:
				proceedToCheckout(cartService, ordersService, user, cartItems);
				break;

			case 5:
				addFlag = false;
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		} while (addFlag);
	}

	private static void loadCartForUser(int userId, Map<Integer, Integer> cartItems, CartService cartService) {
		// clear any previous data
		cartItems.clear();
		List<CartModel> cartList = cartService.getCartByUserId(userId);

		double totalPrice = 0;
		if (!cartList.isEmpty()) {
			for (CartModel cart : cartList) {
				cartItems.put(cart.getProductId(), cart.getQuantity());
				ProductModel product = cartService.getProductById(cart.getProductId());
				if (product != null) {
					totalPrice = totalPrice + (product.getPrice() * cart.getQuantity());
				}
			}
		}
		System.out.println("Cart loaded successfully for user.");
	}

	private static void proceedToCheckout(CartService cartService, OrdersService ordersService, UserModel user,
			Map<Integer, Integer> cartItems) {
		// checking if cart is empty or not
		if (cartItems.isEmpty()) {
			System.out.println("Your cart is Empty. Please add Products to proceed.");
			return;
		}

		double totalPrice = 0;

		// Calculating total price and validate stock
		System.out.println("Proceeding to checkout...");
		List<OrderItemModel> orderItems = new ArrayList<>();

		for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
			int productId = entry.getKey();
			int quantity = entry.getValue();

			ProductModel product = cartService.getProductById(productId);

			// product not found
			if (product == null) {
				System.out.println("Product with Id " + productId + " not Found.");
				return;
			}

			if (product.getStockQuantity() < quantity) {
				System.out.println("Insufficient stock for product: " + product.getName());
				return;
			}

			double itemTotalPrice = product.getPrice() * quantity;
			totalPrice = totalPrice + itemTotalPrice;
			orderItems.add(new OrderItemModel(productId, quantity, itemTotalPrice));

		}

		// Create order
		OrdersModel order = new OrdersModel(user.getUserId(), totalPrice, "Pending", LocalDateTime.now());
		int orderId = ordersService.createOrder(order); // Returns the generated order_id

		if (orderId == -1) {
			System.out.println("Failed to create order. Please try again.");
			return;
		}

		// Add items to order_item table
		for (OrderItemModel orderItem : orderItems) {
			orderItem.setOrderId(orderId);
			boolean success = ordersService.addOrderItem(orderItem);
			if (!success) {
				System.out.println("Failed to add item " + orderItem.getProductId());
				return;
			}
			// Deduct stock
			// cartService.updateProductStock(orderItem.getProductId(),
			// -orderItem.getQuantity());
		}

		// Clear the cart
		cartItems.clear();
		cartService.clearCart(user.getUserId());

		System.out.println("Order placed successfully! Your order ID is: " + orderId);

	}
}
