/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 * Excepcion en el caso de que el valor de login y/o el de email correspondan
 * ya a un usuario ya existente.
 * @author Alain Cosgaya y Alejandro Gomez
 * 
 */
public class SignUpException extends Exception{
    /**
     * Constructor de SignUpException
     * @param message Mensaje enviado desde la implementacion del DAO que se le
     * mostrara al usuario.
     */
    public SignUpException(String message){
        super(message);
    }
}
