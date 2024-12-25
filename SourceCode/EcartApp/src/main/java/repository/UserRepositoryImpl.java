package repository;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import model.UsersModel;

public class UserRepositoryImpl extends DBSTATE implements UsersRepository {
	private static Logger logger = Logger.getLogger(DBConfig.class);
	static {
		PropertyConfigurator.configure(
				"F:\\Project\\E-Comerce Cart System\\SourceCode\\EcartApp\\src\\main\\resources\\application.properties");

	}

	public boolean isAddNewUser(UsersModel model) {
		try {
			stmt = conn
					.prepareStatement("insert into users (user_id,username,email,password,role) values('0',?,?,?,?)");
			stmt.setString(1, model.getUsername());
			stmt.setString(2, model.getEmail());
			stmt.setString(3, model.getPassword());
			stmt.setString(4, model.getRole());
			int value = stmt.executeUpdate();
			return value > 0 ? true : false;

		} catch (Exception e) {
			logger.error("Error while adding user.", e);
			return false;
		}
		finally {
	        if (stmt != null) {
	            try {
	                stmt.close();
	            } catch (Exception e) {
	                logger.error("Failed to close PreparedStatement: ", e);
	            }
	        }
	    }

	}

}
