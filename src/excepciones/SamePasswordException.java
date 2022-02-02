
package excepciones;

/**
 * Excepcion en el caso de que los valores en los campos de password no sean
 * iguales.
 * @author Alain Cosgaya
 */
public class SamePasswordException extends Exception{
    /**
     * Constructor de SamePasswordException.
     * @param message Mensaje enviado desde la vista de la ventana que se
     * mostrara a traves de la bitacora.
     */
    public SamePasswordException(String message){
        super(message);
    }
}
