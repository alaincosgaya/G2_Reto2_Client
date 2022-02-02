/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 *
 * @author Jonathan
 */
public class ConnectException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor de ConnectException.
     *
     * @param message Mensaje enviado desde la implementacion del DAO que se le
     * mostrara al usuario.
     */
    public ConnectException(String message) {
        super(message);
    }
}
