package database;

/* Nommage des elements de la base de donnees
 * (table, attribut, ...)
 */
public interface DatabaseNaming {
	// Nom de la base de donnee
	public static final String DATABASE_NAME = "computer-database-db";

	// Nom des tables
	public static final String COMPUTER_TABLE = "computer";
	public static final String COMPANY_TABLE = "company";

	// Attributs de la table Computer
	public static final String COMPUTER_ID = "id";
	public static final String COMPUTER_NAME = "name";
	public static final String COMPUTER_INTRODUCED = "introduced";
	public static final String COMPUTER_DISCONTINUED = "discontinued";

	// Attributs de la table Company
	public static final String COMPANY_ID = "id";
	public static final String COMPANY_NAME = "name";
}
