package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import database.DatabaseNaming;
import services.MySQLServices;
import services.Services;

/* Connexion a la base de donnee MySQL
 * Cette classe est representee par un pattern Singleton
 */
/**
 * The Class MySQLConnection.
 */
public class MySQLConnection extends DatabaseConnection {

	/** The Constant conn. */
	private static final MySQLConnection conn = new MySQLConnection();
	
	/** The connection. */
	private Connection connection;

	/**
	 * Instantiates a new my sql connection.
	 */
	private MySQLConnection() {
	}

	/**
	 * Gets the single instance of MySQLConnection.
	 *
	 * @return single instance of MySQLConnection
	 */
	public static MySQLConnection getInstance() {
		return conn;
	}

	/* (non-Javadoc)
	 * @see dbconnection.DatabaseConnection#connect()
	 */
	@Override
	public Services connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/"
							+ DatabaseNaming.DATABASE_NAME, user, passwd);

			return new MySQLServices(connection);

		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see dbconnection.DatabaseConnection#deconnect()
	 */
	@Override
	public void deconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
