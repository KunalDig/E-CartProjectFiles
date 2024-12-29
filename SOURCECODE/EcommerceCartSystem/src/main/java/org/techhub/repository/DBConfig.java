package org.techhub.repository;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DBConfig {
	private static Logger logger = Logger.getLogger(DBConfig.class);
	protected static Connection conn;
	protected static PreparedStatement stmt;
	protected static ResultSet rs;

	private static DBConfig db;

	static {
		PropertyConfigurator.configure(
				"C:\\Users\\abcd\\Desktop\\Project\\EcommerceCartSystem\\src\\main\\resources\\application.properties");
	}

	private DBConfig() {
		try {

			File f = new File("");
			String path = f.getAbsolutePath();
			FileInputStream inputStream = new FileInputStream(path + "\\src\\main\\resources\\dbconfig.properties");
			Properties p = new Properties();
			p.load(inputStream);

			logger.info("Loading database driver: " + p.getProperty("driver"));
			Class.forName(p.getProperty("driver"));
			logger.info("Connecting to database: " + p.getProperty("url"));
			conn = DriverManager.getConnection(p.getProperty("url"), p.getProperty("username"),
					p.getProperty("password"));
			logger.info("Connected to database successfully.");

		} catch (Exception e) {
			logger.error("Error connecting to database: " + e.getMessage(), e);
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

	public static ResultSet getResultSet() {
		return rs;
	}
}
