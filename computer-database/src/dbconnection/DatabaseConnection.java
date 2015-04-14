package dbconnection;

import services.Services;

/* Permet la connexion a la base de donnee
 * Fournit le nom d'utilisateur et le password pour la connexion
 */
/**
 * The Class DatabaseConnection.
 */
public abstract class DatabaseConnection {

	/** The user. */
	protected final String user = "admincdb";
	
	/** The passwd. */
	protected final String passwd = "qwerty1234";
	
	/**
	 * Connect.
	 *
	 * @return the services
	 */
	public abstract Services connect();
	
	/**
	 * Deconnect.
	 */
	public abstract void deconnect();
}
