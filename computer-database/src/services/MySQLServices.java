package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import mapper.Mapper;
import model.Company;
import model.Computer;
import database.DatabaseNaming;

public final class MySQLServices extends Services {
	
	public MySQLServices(Connection conn) {
		super(conn);
	}
	
	private void closeResultSetAndStatement(
			Statement statement, ResultSet result) {
		
		try {
			if (result != null) {
				result.close();
			}
			
			if (statement != null) {
				statement.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Computer> listComputers() {
		Statement statement = null;
		ResultSet result = null;
		
		try {
			statement = conn.createStatement();
			result = statement.executeQuery(
					"SELECT " + DatabaseNaming.COMPUTER_NAME +
					"," + DatabaseNaming.COMPUTER_ID + 
					" FROM " + DatabaseNaming.COMPUTER_TABLE +
					" ORDER BY name;");
			return Mapper.toComputerList(result);
			
		} catch (SQLException e) {
			return null;
			
		} finally { // fermeture du result et du statement
			closeResultSetAndStatement(statement, result);
		}
	}

	@Override
	public List<Company> listCompanies() {
		Statement statement = null;
		ResultSet result = null;

		try {
			statement = conn.createStatement();
			result = statement.executeQuery(
					"SELECT " + DatabaseNaming.COMPANY_NAME +
					"," + DatabaseNaming.COMPANY_ID +
					" FROM " + DatabaseNaming.COMPANY_TABLE +
					" ORDER BY " + DatabaseNaming.COMPANY_NAME + ";");
			return Mapper.toCompanyList(result);

		} catch (SQLException e) {
			return null;

		} finally {
			closeResultSetAndStatement(statement, result);
		}
	}

	@Override
	public void showComputerDetails(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean addComputer(Computer computer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateComputer(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteComputer(long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
