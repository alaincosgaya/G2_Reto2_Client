/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import interfaces.ContratoInterface;

/**
 * Factoria de Implementaciones de contratos.
 * @author Alain Cosgaya 
 */
public class ContratoManagerFactory {
    public static ContratoInterface getContratoManagerImplementation(){
        ContratoInterface contratoManager  = new ContratoManagerImplementation();
        return contratoManager;
    }
}
