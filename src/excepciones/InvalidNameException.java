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
public class InvalidNameException extends Exception {

    /**
     * Constructor de InvalidNameException.
     *
     * @param message Mensaje enviado desde la implementacion del DAO que se le
     * mostrara al usuario.
     */
    public InvalidNameException(String message) {
        super(message);
    }
}
