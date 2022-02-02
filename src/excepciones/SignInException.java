/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 * Excepcion en el caso de que los parametros del campo login y password
 * no sean correctos.
 * @author Alejandro Gomez y Alain Cosgaya
 */
public class SignInException extends Exception{
    /**
     * Constructor de SignInException
     * @param message Mensaje enviado desde la implementacion del DAO que se le
     * mostrara al usuario.
     */
    public SignInException(String message){
        super(message);
    }
}
