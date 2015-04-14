package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dao.DatabaseNaming.*;

public enum ConnectionFactory {
	INSTANCE;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new DAOException();
		}
	}

	public Connection openConnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"
					+ DATABASE_NAME, USER, PASSWD);
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
	
	public void closeResultSetAndStatement(Statement statement,
			ResultSet result) {

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
