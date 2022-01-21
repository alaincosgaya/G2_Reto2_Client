/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import clases.ContratoEntity;
import java.util.Collection;

/**
 *
 * @author 2dam
 */
public interface ContratoInterface {

    public Collection<ContratoEntity> getAllContratos();

    public Collection<ContratoEntity> getContratosGranjero(String idGranjero);

    public ContratoEntity getContrato(String idContrato);

    public Collection<ContratoEntity> getContratosTrabajador(String idTrabajador);

    public Collection<ContratoEntity> getContratosGranja(String idGranja);

    public void despedirTrabajador(String idTrabajador, String idGranja);

    public ContratoEntity cambiarSueldo(String idTrabajador, String idGranja, String salario);
    
    public void contratarTrabajador(ContratoEntity contrato);
}
