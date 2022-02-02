/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import interfaces.ZonaInterface;

/**
 *
 * @author 2dam
 */
public class ZonaManagerFactory {
    public static ZonaInterface getZonaManagerImplementation(){
        ZonaInterface zonaManager  = new ZonaManagerImplementation();
        return zonaManager;
    }
}
