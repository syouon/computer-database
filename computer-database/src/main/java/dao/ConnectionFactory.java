package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import exception.ConnectionException;
import exception.DAOException;
import exception.RollBackException;

@Component
public class ConnectionFactory {

	@Autowired
	private DataSource datasource;
	private Logger logger;
	private static final ThreadLocal<Connection> LOCALCONN = new ThreadLocal<>();

	private ConnectionFactory() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	public Connection openConnection() {
		try {
			if (LOCALCONN.get() == null) {
				LOCALCONN.set(datasource.getConnection());
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
			if (result != null && !result.isClosed()) {
				result.close();
			}

			if (statement != null && !statement.isClosed()) {
				statement.close();
			}

			logger.debug("ResultSet and Statement closed");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DAOException();
		}
	}
}
