
package excepciones;

/**
 * Excepcion en el caso de que el valor del campo password no tenga una longitud
 * de 8 caracteres
 * @author Alain Cosgaya
 */
public class PasswordLengthException extends Exception{
    /**
     * Constructor de PasswordLengthException.
     * @param message Mensaje enviado desde la vista de la ventana que se
     * mostrara a traves de la bitacora.
     */
    public PasswordLengthException(String message){
        super(message);
    }

    
}
