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
	private static final ThreadLocal<Connection> LOCALCONN = new ThreadLocal<>();

	private ConnectionFactory() {
		// Logger
		logger = LoggerFactory.getLogger(this.getClass());

		// Properties
		prop = Utils.loadProperties("database.properties");

		// Chargement du Driver
		try {
			Class.forName(prop.getProperty("driver"));
			logger.debug("Driver loaded");
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
			throw new DriverNotFoundException();
		}

		// Pool de connexion
		BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl(prop.getProperty("url"));
		config.setUsername(prop.getProperty("user"));
		config.setPassword(prop.getProperty("password"));
		config.setMinConnectionsPerPartition(1);
		config.setMaxConnectionsPerPartition(5);
		config.setPartitionCount(2);
		try {
			pool = new BoneCP(config);
			logger.debug("BoneCP initialized");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new BoneCPInitException();
		}
	}

	public Connection openConnection() {
		try {
			if (LOCALCONN.get() == null) {
				LOCALCONN.set(pool.getConnection());
			}

			logger.debug("Connection get");
			return LOCALCONN.get();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new ConnectionException();
		}
	}

	public void closeConnection() {
		try {
			Connection connection = LOCALCONN.get();
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
			LOCALCONN.remove();
			logger.debug("Connection closed");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new ConnectionException();
		}
	}

	public static ConnectionFactory getInstance() {
		return INSTANCE;
	}

	public void startTransaction() throws SQLException {
		Connection conn = LOCALCONN.get();
		conn.setAutoCommit(false);
	}

	public void endTransaction() throws SQLException {
		Connection conn = LOCALCONN.get();
		conn.commit();
		conn.setAutoCommit(true);
	}

	public void rollback() {
		Connection conn = LOCALCONN.get();
		try {
			conn.rollback();
			logger.debug("Rollback done");
		} catch (SQLException e) {
			logger.error("Rollback failed: " + e.getMessage());
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

			logger.debug("ResultSet and Statement closed");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException();
		}
	}
}
