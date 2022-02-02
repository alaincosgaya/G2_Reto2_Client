
package excepciones;

/**
 * Excepcion en el caso de que el email introducido no cumpla con el patron
 * preestablecido
 * @author Alain Cosgaya
 */
public class EmailPatternException extends Exception{
    /**
     * Constructor de EmailPatternException.
     * @param message Mensaje enviado desde la vista de la ventana que se
     * mostrara a traves de la bitacora.
     */
    public EmailPatternException(String message){
        super(message);
    }
}
