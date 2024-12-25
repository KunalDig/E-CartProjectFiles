package client;

import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import model.UsersModel;
import repository.DBConfig;
import service.UsersService;
import service.UsersServiceImpl;

public class EcartApplication {
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
				do {
					System.out.println("Login as admin or user(Only if both are already registered)");
					System.out.println("ENTER YOUR CHOICE : 1.admin 2.user 3.Exit from login");
					int AUchoie = sc.nextInt();
					switch (AUchoie) {
					case 1:// logic for admin login and his functionality

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
