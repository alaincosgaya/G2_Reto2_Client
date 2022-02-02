/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import interfaces.GranjaInterface;

/**
 *
 * @author Alejandro Gomez
 */
public class GranjaManagerFactory {
    public static GranjaInterface getGranjaInterface(){
        GranjaInterface granjaInterface  = new GranjaManagerImplementation();
        return granjaInterface;
    }
}
