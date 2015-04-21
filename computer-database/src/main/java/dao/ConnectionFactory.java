package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import exception.DAOException;
import exception.PropertiesNotFoundException;

public enum ConnectionFactory {
	INSTANCE;

	private Properties prop;

	static {
		// Chargement du driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new DAOException();
		}
	}

	private ConnectionFactory() {
		prop = new Properties();
		InputStream input = null;
		try {
			input = getClass().getClassLoader().getResourceAsStream(
					"database.properties");

			if (input != null) {
				prop.load(input);
			}

		} catch (IOException e) {
			throw new PropertiesNotFoundException();
		} /*
		 * finally { try { input.close(); } catch (IOException e) { throw new
		 * PropertiesNotFoundException(); } }
		 */
	}

	public Connection openConnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(prop.getProperty("url"),
					prop.getProperty("user"), prop.getProperty("password"));
		} catch (SQLException e) {
			throw new DAOException();
		}

		return conn;
	}

	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new DAOException();
		}
	}

	public static ConnectionFactory getInstance() {
		return INSTANCE;
	}

	public void closeResultSetAndStatement(Statement statement, ResultSet result) {

		try {
			if (result != null) {
				result.close();
			}

			if (statement != null) {
				statement.close();
			}

		} catch (SQLException e) {
			throw new DAOException();
		}
	}
}
