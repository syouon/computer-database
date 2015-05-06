package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import exception.DAOException;

@Component
public class ConnectionFactory {

	@Autowired
	private DataSource datasource;
	private Logger logger;

	private ConnectionFactory() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	public Connection openConnection() {
		return DataSourceUtils.getConnection(datasource);
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
