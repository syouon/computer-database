package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.Utils;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import exception.BoneCPInitException;
import exception.ConnectionException;
import exception.DAOException;
import exception.DriverNotFoundException;
import exception.RollBackException;

public enum ConnectionFactory {
	INSTANCE;

	private Properties prop;
	private BoneCP pool;
	private Logger logger;
	private final ThreadLocal<Connection> localConn = new ThreadLocal<>();

	private ConnectionFactory() {
		// Logger
		logger = LoggerFactory.getLogger(this.getClass());

		// Properties
		prop = Utils.loadProperties("database.properties");

		// Chargement du Driver
		try {
			Class.forName(prop.getProperty("driver"));
			logger.info("Driver loaded");
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
			logger.info("BoneCP initialized");
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new BoneCPInitException();
		}
	}

	public Connection openConnection() {
		try {
			if (localConn.get() == null) {
				localConn.set(pool.getConnection());
			}

			logger.info("Connection get");
			return localConn.get();
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new ConnectionException();
		}
	}

	public void closeConnection() {
		try {
			Connection connection = localConn.get();
			if (connection != null) {
				connection.close();
			}
			localConn.remove();
			logger.info("Connection closed");
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new ConnectionException();
		}
	}

	public static ConnectionFactory getInstance() {
		return INSTANCE;
	}

	public void startTransaction() throws SQLException {
		Connection conn = localConn.get();
		conn.setAutoCommit(false);
	}

	public void endTransaction() throws SQLException {
		Connection conn = localConn.get();
		conn.commit();
		conn.setAutoCommit(true);
	}

	public void rollback() {
		Connection conn = localConn.get();
		try {
			conn.rollback();
			logger.info("Rollback done");
		} catch (SQLException e) {
			logger.debug("Rollback failed: " + e.getMessage());
			throw new RollBackException();
		}
	}

	public void closeResultSetAndStatement(Statement statement, ResultSet result) {

		try {
			if (result != null) {
				result.close();
			}

			if (statement != null) {
				statement.close();
			}

			logger.info("ResultSet and Statement closed");
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new DAOException();
		}
	}
}
