package client;

import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import model.ProductsModel;
import model.UsersModel;
import repository.DBConfig;
import repository.DBSTATE;
import service.AuthService;
import service.AuthServiceImpl;
import service.ProductsService;
import service.ProductsServiceImpl;
import service.UsersService;
import service.UsersServiceImpl;

public class EcartApplication extends DBSTATE{
	private static Logger logger = Logger.getLogger(DBConfig.class);
	static {
		PropertyConfigurator.configure(
				"F:\\Project\\E-Comerce Cart System\\SourceCode\\EcartApp\\src\\main\\resources\\application.properties");

	}

	public static void main(String[] args) {
		logger.info("Main method started...");
		boolean flag = true;
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("1. Register as admin or normal user(user) \n2. Login as admin or user : \n3. Exit");
			System.out.println("ENTER YOUR CHOICE : ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:// Register as admin or normal user(user)
				UsersService usersService = new UsersServiceImpl();
				UsersModel usersModel = new UsersModel();
				System.out.println("Enter Username:");
				String username = sc.next();
				usersModel.setUsername(username);

				System.out.println("Enter Email:");
				String email = sc.next();
				usersModel.setEmail(email);

				System.out.println("Enter Password:");
				String password = sc.next();
				usersModel.setPassword(password);

				System.out.println("Enter Role ('admin' or 'normal'):");
				String role = sc.next();
				usersModel.setRole(role);
				boolean b = usersService.isAddNewUser(usersModel);
				if (b) {
					logger.info("new user added");
					System.out.println("user added succesfully...");
				} else {
					System.out.println("user not added");
					logger.warn("user cannot be added");
				}
				break;

			case 2: // Login as admin or user
				boolean AUflag = true;
				AuthService authService = new AuthServiceImpl();
				do {
					System.out.println("Login as admin or user(Only if both are already registered)");
					System.out.println("Enter username and password : ");
					username = sc.next();
					password = sc.next();
					role = authService.authenticate(username, password);
//					System.out.println(role);
					if (!"admin".equalsIgnoreCase(role) && !"normal".equalsIgnoreCase(role)) {
						System.out.println("You are not registered.");
						break;
					}
					System.out.println("WELCOME!!!");
					logger.info("User (admin or normal) logged in.");
					System.out.println("ENTER YOUR CHOICE : 1.admin 2.user 3.Exit from login");
					int AUchoie = sc.nextInt();
					switch (AUchoie) {
					case 1:// logic for admin login and his functionality
						if (role == "admin") {
							System.out.println("Not a admin!!! Cannot continue");
							break;
						}
						boolean adminFlag = true;
						do {
							ProductsModel productsModel = new ProductsModel();
							ProductsService productsService = new ProductsServiceImpl();
							System.out.println(
									"1. Add Product\n2. Update Product\n3. Delete Product\n4. View Stock\n5. Display all products\n6. view all orders\n7. Update Order Status\n8. View Transaction History\n9. Exit");
							System.out.println("ENTER YOUR CHOICE : ");
							int funcChoice = sc.nextInt();
							switch (funcChoice) {
							case 1:// Add Product
								sc.nextLine();

								System.out.println("Enter name of product : ");
								String productName = sc.nextLine();
								productsModel.setName(productName);

								System.out.println("Enter product description : ");
								String productDescription = sc.nextLine();
								productsModel.setDescription(productDescription);

								System.out.println("Enter product price : ");
								double productPrice = sc.nextDouble();
								sc.nextLine();
								productsModel.setPrice(productPrice);

								System.out.println("Enter product stock : ");
								int productStock = sc.nextInt();
								sc.nextLine();
								productsModel.setStock_quantity(productStock);

								b = productsService.addProduct(productsModel);
								if (b) {
									System.out.println("New product is successfully added...");
								} else {
									System.out.println("New product is not added...");
								}

								break;
							case 2:// Update Product
								sc.nextLine();
								System.out.println("Enter product ID : ");
								int productId = sc.nextInt();
								sc.nextLine();
								productsModel.setProduct_id(productId);

								System.out.println(
										"Enter the field to update (name, description, price, stock_quantity):");
								String fieldToUpdate = sc.nextLine();
								
								System.out.println("Enter the new value:");
						        Object newValue = null;
						        if (fieldToUpdate.equalsIgnoreCase("name")) {
						            newValue = sc.nextLine();
						        } else if (fieldToUpdate.equalsIgnoreCase("description")) {
						            newValue = sc.nextLine();
						        } else if(fieldToUpdate.equalsIgnoreCase("price")) {
						            newValue = sc.nextDouble();
						        }
						        else if(fieldToUpdate.equalsIgnoreCase("stock_quantity")) {
						        	newValue = sc.nextInt();
						        }
						        else {
						        	System.out.println("Wrong field");
						        }
								
						        b = productsService.updateProduct(productsModel, fieldToUpdate, newValue);
						        if(b) {
						        	System.out.println("Product updated succesfully.");
						        }
						        else {
						        	System.out.println("Product is not updated.");
						        }

								break;

							case 3:// Delete Product
								sc.nextLine();
								System.out.println("Enter ID of product to be deleted : ");
								int productIdToDelete = sc.nextInt();
								productsModel.setProduct_id(productIdToDelete);
								
								b = productsService.deleteProduct(productsModel);
								if(b) {
									System.out.println("Product removed succesfully.");
								}
								else {
									System.out.println("Product could not be removed.");
								}
								break;
							case 4:// View Stock
								sc.nextLine();
								System.out.println("Enter ID of product whose stock you want to view : ");
								int productIdForStock = sc.nextInt();
								productsModel.setProduct_id(productIdForStock);
								productsService.viewStock(productsModel);
								break;
							case 5:// display all products
								productsService.displayAllProducts();
								break;
							case 6:// View All Orders
								productsService.viewAllOrders();
								break;
							case 7:// Update Order Status

								break;
							case 8:// View Transaction History

								break;
							case 9:// Exit
								adminFlag = false;

								break;

							default:
								System.out.println("Invalid choice");
								break;
							}
						} while (adminFlag);
						break;

					case 2: // logic for normal user login and his functionality
						break;

					case 3: // logged out
						System.out.println("Logged out");
						AUflag = false;
						break;
					default:
						System.out.println("Invalid choice...");
						break;
					}
				} while (AUflag);
				break;

			case 3: // Exit from main app
				logger.info("Exited from application");
				flag = false;

				break;
			default:
				System.out.println("Invalid choice...");
				break;
			}
		} while (flag);
		sc.close();
	}
	
	

}
