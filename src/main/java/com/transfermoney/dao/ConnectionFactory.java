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

	public static Connection getConnection() {

		String url = PropertiesUtil.getProperty("h2_connection_url");
		String user = PropertiesUtil.getProperty("h2_user");
		String passwd = PropertiesUtil.getProperty("h2_password");

		try {
			Class.forName(PropertiesUtil.getProperty("h2_driver"));

			return DriverManager.getConnection(url, user, passwd);

		} catch (ClassNotFoundException | SQLException e) {
			logger.error("Error in driver registration - " + e);
			return null;
		}

	}

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
