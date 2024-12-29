package org.techhub.repository;

import java.sql.Timestamp;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.techhub.model.UserModel;

public class AuthRepositoryImpl extends DBSTATE implements AuthRepository {

	private static Logger logger = Logger.getLogger(AuthRepositoryImpl.class);

	static {
		PropertyConfigurator.configure(
				"C:\\Users\\abcd\\Desktop\\Project\\EcommerceCartSystem\\src\\main\\resources\\application.properties");
	}

	@Override
	public boolean registerUser(UserModel user) {
		try {

			stmt = conn
					.prepareStatement("insert into users(username,email,password,created_at,role) values (?,?,?,?,?)");
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getPassword());
			stmt.setTimestamp(4, Timestamp.valueOf(user.getCreatedAt()));
			stmt.setString(5, user.getRole());

			int rowsInserted = stmt.executeUpdate();
			System.out.println(rowsInserted);
			return rowsInserted > 0;
		} catch (Exception e) {
			logger.error("Error registering user: " + e.getMessage(), e);
			return false;
		}
	}

	@Override
	public Optional<UserModel> login(String username, String password) {
		try {
			stmt = conn.prepareStatement("select * from users where username = ? and password = ?");
			stmt.setString(1, username);
			stmt.setString(2, password);

			rs = stmt.executeQuery();
			if (rs.next()) {
				UserModel user = new UserModel(rs.getInt("user_id"), rs.getString("username"), rs.getString("email"),
						rs.getString("password"), rs.getTimestamp("created_at").toLocalDateTime(),
						rs.getString("role"));
				return Optional.of(user);
			}
		} catch (Exception e) {
			logger.error("Error during login: " + e.getMessage(), e);
		}
		return Optional.empty();
	}

	@Override
	public boolean isAdmin(String username) {
		try {
			stmt = conn.prepareStatement("select role from users where username=?");
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			if (rs.next()) {
				boolean isAdmin = rs.getString("role").equalsIgnoreCase("admin");
				if (isAdmin) {
					logger.info("User " + username + " is an admin.");
				} else {
					logger.info("User " + username + " is not an admin.");
				}
				return isAdmin;
			}
		} catch (Exception e) {
			logger.error("Error checking admin status for user " + username + ": " + e.getMessage(), e);

		}
		return false;
	}

}
