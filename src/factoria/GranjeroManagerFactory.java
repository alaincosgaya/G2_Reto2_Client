/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import interfaces.GranjaInterface;
import interfaces.GranjeroInterface;

/**
 *
 * @author Alejandro
 */
public class GranjeroManagerFactory {
    public static GranjeroInterface getGranjaInterface(){
        GranjeroInterface granjeroInterface  = new GranjeroManagerImplementation();
        return granjeroInterface;
    }
}
