package client;

import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import repository.DBConfig;

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

				break;

			case 2: // Login as admin or user
				boolean AUflag = true;
				do {
					System.out.println("Login as admin or user(if both are already registered)");
					System.out.println("ENTER YOUR CHOICE : 1.admin 2.user 3.Exit from login");
					int AUchoie = sc.nextInt();
					switch (AUchoie) {
					case 1:// logic for admin login and his functionality

						break;

					case 2: // logic for normal user login and his functionality
						break;

					case 3: //
						System.out.println("Logged out");
						AUflag = false;
						break;
					default:
						System.out.println("Invalid choice...");
						break;
					}
				} while (AUflag);
				break;

			case 3: // Exit
				logger.info("Exited from application");
				flag = false;

				break;
			default:
				System.out.println("Invalid choice...");
				break;
			}
		} while (flag);

	}

}
