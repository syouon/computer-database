package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import exception.DAOException;
import exception.PropertiesNotFoundException;

public enum ConnectionFactory {
	INSTANCE;

	private Properties prop;
	private BoneCP pool;

	private ConnectionFactory() {
		// Properties
		prop = new Properties();
		InputStream input = null;
		try {
			input = getClass().getClassLoader().getResourceAsStream(
					"database.properties");

			if (input != null) {
				prop.load(input);
			}

		} catch (IOException e) {
			System.out.println("PROPERTIES: " + e.getMessage());
			throw new PropertiesNotFoundException();
		} /*
		 * finally { try { input.close(); } catch (IOException e) { throw new
		 * PropertiesNotFoundException(); } }
		 */
		
		// Chargement du Driver
		try {
			Class.forName(prop.getProperty("driver"));
		} catch (ClassNotFoundException e) {
			System.out.println("DRIVER: " + e.getMessage());
			throw new DAOException();
		}
		
		// Pool de connexion
		BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl(prop.getProperty("url"));
		config.setUsername(prop.getProperty("user"));
		config.setPassword(prop.getProperty("password"));
		config.setMinConnectionsPerPartition(1);
		config.setMaxConnectionsPerPartition(10);
		config.setPartitionCount(2);
		try {
			pool = new BoneCP(config);
		} catch (SQLException e) {
			System.out.println("BONECP: " + e.getMessage());
			throw new DAOException();
		}
	}

	public Connection openConnection() {
		Connection conn = null;

		try {
			conn = /*DriverManager.getConnection(prop.getProperty("url"),
					prop.getProperty("user"), prop.getProperty("password"));*/
					pool.getConnection();
		} catch (SQLException e) {
			System.out.println("BONECP GETCONNECTTION: " + e.getMessage());
			throw new DAOException();
		}

		return conn;
	}

	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("CONNECTION CLOSE: " + e.getMessage());
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
			System.out.println("CLOSE RESOURCES: " + e.getMessage());
			throw new DAOException();
		}
	}
}
