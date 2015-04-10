package database;

/* Nommage des elements de la base de donnees
 * (table, attribut, ...)
 */
public interface DatabaseNaming {

	public static final String COMPUTER_TABLE = "computer";
	public static final String COMPANY_TABLE = "company";
	
	public static final String COMPUTER_ID = "id";
	public static final String COMPANY_ID = "id";
	
	public static final String COMPANY_NAME = "name";
	
	public static final String COMPUTER_NAME = "name";
	public static final String COMPUTER_INTRODUCED = "introduced";
	public static final String COMPUTER_DISCONTINUED = "discontinued";
}
