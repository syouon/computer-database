package dao;

import static database.DatabaseNaming.COMPANY_ID;
import static database.DatabaseNaming.COMPANY_NAME;
import static database.DatabaseNaming.COMPANY_TABLE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import mapper.Mapper;
import model.Company;
import dao.ConnectionFactory;
import util.Utils;

public class ConcreteCompanyDAO implements CompanyDAO {

	@Override
	public List<Company> findAll() {
		Connection conn = ConnectionFactory.getInstance().openConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = conn.prepareStatement("SELECT " + COMPANY_NAME + ","
					+ COMPANY_ID + " FROM " + COMPANY_TABLE + " ORDER BY "
					+ COMPANY_NAME + ";");
			result = statement.executeQuery();
			return Mapper.toCompanyList(result);

		} catch (SQLException e) {
			throw new DAOException();
		} finally {
			Utils.closeResultSetAndStatement(statement, result);
			ConnectionFactory.getInstance().closeConnection(conn);
		}
	}
}
