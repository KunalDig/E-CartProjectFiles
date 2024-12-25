package repository;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class AuthRepoImpl extends DBSTATE implements AuthRepo {

	private static Logger logger = Logger.getLogger(DBConfig.class);
	static {
		PropertyConfigurator.configure(
				"F:\\Project\\E-Comerce Cart System\\SourceCode\\EcartApp\\src\\main\\resources\\application.properties");

	}

	public String authenticate(String username, String password) {
		try {
			String role = null;
			stmt = conn.prepareStatement("select role from users where username = ? and password = ?");
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				role = rs.getString("role");
			} else {
				logger.warn("User not found or invalid credentials.");
			}
			return role;

		} catch (Exception e) {
			logger.error("User can't be authenticated ", e);
			return null;
		}

	}
}
