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
 * @author Idoia Ormaetxea
 */
public class ClienteServidorConexionException extends Exception{
    public ClienteServidorConexionException(String message){
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE,message,this);
    }
}