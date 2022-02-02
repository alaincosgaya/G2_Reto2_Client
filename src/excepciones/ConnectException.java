package excepciones;

/**
 * Excepcion en el caso de que haya un error a la hora de conectarse a la base 
 * de datos.
 * @author Alain Cosgaya
 */
public class ConnectException extends Exception{

	private static final long serialVersionUID = 1L;
        /**
         * Constructor de ConnectException.
         * @param message Mensaje enviado desde la implementacion del DAO que se
         * le mostrara al usuario.
         */
	public ConnectException(String message) {
		super(message);
	}
	
}
