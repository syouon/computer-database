package dbconnection;

import services.Services;

/* Permet la connexion a la base de donnee
 * Fournit le nom d'utilisateur et le password pour la connexion
 */
public abstract class DatabaseConnection {

	protected final String user = "admincdb";
	protected final String passwd = "qwerty1234";
	
	public abstract Services connect();
}
