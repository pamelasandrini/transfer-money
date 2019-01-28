package com.transfermoney.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.RunScript;

public abstract class ConnectionFactory {

	// TODO: add config file
	private static String url = "jdbc:h2:mem:transfer-money;DB_CLOSE_DELAY=-1";
	private static String user = "sa";
	private static String passwd = "";

	public static Connection getConnection() {

		try {
			Class.forName("org.h2.Driver");

			return DriverManager.getConnection(url, user, passwd);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void populateTestData() {
		System.out.println("running populateTestData using H2 db in memory");

		try (Connection conn = ConnectionFactory.getConnection()) {

			RunScript.execute(conn, new FileReader("src/test/resources/create.sql"));

		} catch (SQLException e) {

			System.out.println("populateTestData(): Error populating user data: " + e);
			throw new RuntimeException(e);

		} catch (FileNotFoundException e) {

			System.out.println("populateTestData(): Error finding test script file " + e);
			throw new RuntimeException(e);

		}
	}
}
