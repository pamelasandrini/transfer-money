package com.transfermoney.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.h2.tools.RunScript;

public class ConnectionFactory {

	static final Logger logger = Logger.getLogger(ConnectionFactory.class);
	// TODO: add config file
	private static String url = "jdbc:h2:mem:transfer-money;DB_CLOSE_DELAY=-1";
	private static String user = "sa";
	private static String passwd = "";

	public static Connection getConnection() {

		try {
			Class.forName("org.h2.Driver");

			return DriverManager.getConnection(url, user, passwd);

		} catch (ClassNotFoundException | SQLException e) {
			logger.error("Error in driver registration - " + e);
			return null;
		}

	}

	public void populateTestData() {
		logger.info("running populateTestData using H2 db in memory");

		try (Connection conn = ConnectionFactory.getConnection()) {

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(getClass().getResourceAsStream("/create.sql")));

			RunScript.execute(conn, reader);

		} catch (SQLException e) {

			logger.error("Error populating user data - " + e);
			throw new RuntimeException(e);

		}
	}
}
