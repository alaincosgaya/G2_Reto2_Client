
package excepciones;

/**
 * Excepcion en el caso de que el valor del campo password no contenga ningun
 * digito.
 * @author Alain Cosgaya
 */
public class PasswordNumException extends Exception{
    /**
     * Constructor de PasswordNumException.
     * @param message Mensaje enviado desde la vista de la ventana que se
     * mostrara a traves de la bitacora.
     */
    public PasswordNumException(String message){
        super(message);
    }
}
