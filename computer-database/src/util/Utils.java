package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.DAOException;

public class Utils {

	/**
	 * Close result set and statement.
	 *
	 * @param statement
	 *            the statement
	 * @param result
	 *            the result
	 */
	public static void closeResultSetAndStatement(Statement statement,
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
