package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import exception.BoneCPInitException;
import exception.ConnectionException;
import exception.DAOException;
import exception.DriverNotFoundException;
import exception.PropertiesNotFoundException;

public enum ConnectionFactory {
	INSTANCE;

	private Properties prop;
	private BoneCP pool;
	private Logger logger;

	private ConnectionFactory() {
		// Logger
		logger = LoggerFactory.getLogger(this.getClass());

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
			logger.debug(e.getMessage());
			throw new PropertiesNotFoundException();
		} /*
		 * finally { try { input.close(); } catch (IOException e) { throw new
		 * PropertiesNotFoundException(); } }
		 */

		// Chargement du Driver
		try {
			Class.forName(prop.getProperty("driver"));
		} catch (ClassNotFoundException e) {
			logger.debug(e.getMessage());
			throw new DriverNotFoundException();
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
			logger.debug(e.getMessage());
			throw new BoneCPInitException();
		}
	}

	public Connection openConnection() {
		Connection conn = null;

		try {
			conn = pool.getConnection();
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new ConnectionException();
		}

		return conn;
	}

	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new ConnectionException();
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
			logger.debug(e.getMessage());
			throw new DAOException();
		}
	}
}
