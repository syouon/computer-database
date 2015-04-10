package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import services.MySQLServices;
import services.Services;

/* Connexion a la base de donnee MySQL
 * Cette classe est representee par un pattern Singleton
 */
public final class MySQLConnection extends DatabaseConnection {

	private static final MySQLConnection conn = new MySQLConnection();
	
	private MySQLConnection() {}
	
	public static MySQLConnection getInstance() {
		return conn;
	}
	
	@Override
	public Services connect() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/computer-database-db",
					user,
					passwd);
			
			return new MySQLServices(connection);
			
		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}
}
