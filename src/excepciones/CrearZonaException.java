/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alain Cosgaya
 */
public class CrearZonaException extends Exception{
     public CrearZonaException(String message){
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE,message,this);
    }
}