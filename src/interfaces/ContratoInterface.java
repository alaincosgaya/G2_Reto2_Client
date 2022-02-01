/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import clases.ContratoEntity;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import java.util.Collection;

/**
 *
 * @author 2dam
 */
public interface ContratoInterface {

    public Collection<ContratoEntity> getAllContratos() throws ClienteServidorConexionException, BDServidorException;

    public Collection<ContratoEntity> getContratosGranjero(String idGranjero) throws ClienteServidorConexionException, BDServidorException;

    public ContratoEntity getContrato(String idContrato) throws ClienteServidorConexionException, BDServidorException;

    public Collection<ContratoEntity> getContratosTrabajador(String idTrabajador) throws ClienteServidorConexionException, BDServidorException;

    public Collection<ContratoEntity> getContratosGranja(String idGranja) throws ClienteServidorConexionException, BDServidorException;

    public void despedirTrabajador(String idTrabajador, String idGranja) throws ClienteServidorConexionException, BDServidorException;

    public ContratoEntity cambiarSueldo(String idTrabajador, String idGranja, String salario) throws ClienteServidorConexionException, BDServidorException;
    
    public void contratarTrabajador(ContratoEntity contrato) throws ClienteServidorConexionException, BDServidorException;
}
