/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

import java.sql.SQLException;

/**
 * Excepcion en el caso de que haya un problema interno en la base de datos
 * a la hora de actualizar la tabla signin.
 * @author Alejandro Gomez y Alain Cosgaya
 * 
 */
public class UpdateException extends SQLException{
    /**
     * Constructor de UpdateException
     * @param message Mensaje enviado desde la implementacion del DAO que se le
     * mostrara al usuario.
     */
    public UpdateException(String message){
        super(message);
    }
}
