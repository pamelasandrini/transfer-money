package com.transfermoney.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.h2.tools.RunScript;

import com.transfermoney.util.PropertiesUtil;

public abstract class ConnectionFactory {

	private static final Logger logger = Logger.getLogger(ConnectionFactory.class);
	private static final String url = PropertiesUtil.getProperty("h2_connection_url");
	private static final String user = PropertiesUtil.getProperty("h2_user");
	private static final String passwd = PropertiesUtil.getProperty("h2_password");
	private static final String driver = PropertiesUtil.getProperty("h2_driver");

	/**
	 * Get h2 db connection.
	 * @return
	 */
	public static Connection getConnection() {

		try {
			Class.forName(driver);

			return DriverManager.getConnection(url, user, passwd);

		} catch (ClassNotFoundException | SQLException e) {
			logger.error("Error in driver registration - " + e);
			throw new RuntimeException(e);
		}

	}

	/**
	 * Execute sql script to create db and populate testing data.
	 */
	public static void populateTestData() {
		logger.info("running populateTestData using H2 db in memory");

		try (Connection conn = ConnectionFactory.getConnection()) {

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(ConnectionFactory.class.getResourceAsStream("/create.sql")));

			RunScript.execute(conn, reader);

		} catch (SQLException e) {

			logger.error("Error populating user data - " + e);
			throw new RuntimeException(e);

		}
	}
}
