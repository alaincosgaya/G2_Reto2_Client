/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import interfaces.ContratoInterface;
import interfaces.TrabajadorInterface;

/**
 *
 * @author 2dam
 */
public class TrabajadorManagerFactory {
    public static TrabajadorInterface getTrabajadorManagerImplementation(){
        TrabajadorInterface trabajadorManager  = new TrabajadorManagerImplementation();
        return trabajadorManager;
    }
}
