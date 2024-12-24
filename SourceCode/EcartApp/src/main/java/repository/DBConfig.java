package repository;

import java.io.File;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DBConfig {
	private static Logger logger = Logger.getLogger(DBConfig.class);
	private static DBConfig db;
	protected static Connection conn;
	protected static PreparedStatement stmt;
	protected static CallableStatement cStmt;
	protected static ResultSet rs;

	static {
		PropertyConfigurator.configure(
				"F:\\Project\\E-Comerce Cart System\\SourceCode\\EcartApp\\src\\main\\resources\\application.properties");

	}

	private DBConfig() {
		try {

			File f = new File("");
			String path = f.getAbsolutePath() + "\\src\\main\\resources\\dbconfig.properties";

			FileInputStream inputSream = new FileInputStream(path);
			Properties p = new Properties();
			p.load(inputSream);
			String driverClassName = p.getProperty("driver");
			String username = p.getProperty("username");
			String password = p.getProperty("password");
			String url = p.getProperty("url");

			Class.forName(driverClassName);
			conn = DriverManager.getConnection(url, username, password);
			logger.info("DBConfig : Database is connected successfully...");
		} catch (Exception e) {
			logger.error("DBConfig : Database is not comfigured... " + e);
		}
	}

	public static DBConfig getInstance() {
		if (db == null) {
			db = new DBConfig();
		}
		return db;
	}

	public static Connection getConn() {
		return conn;
	}

	public static PreparedStatement getStatement() {
		return stmt;
	}

	public static CallableStatement getCStatement() {
		return cStmt;
	}

	public static ResultSet getResultSet() {
		return rs;

	}
}
