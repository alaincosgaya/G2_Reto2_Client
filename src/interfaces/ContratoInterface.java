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
 * Interfaz de la implementacion de contratos.
 *
 * @author Alain Cosgaya
 */
public interface ContratoInterface {

    /**
     * Metodo para la recuperación de todos los contratos.
     *
     * @return Coleccion de contratos.
     * @throws ClienteServidorConexionException
     * @throws BDServidorException
     */
    public Collection<ContratoEntity> getAllContratos() throws ClienteServidorConexionException, BDServidorException;

    /**
     * Metodo para la recuperacion de los contratos realizados por un granjero
     *
     * @param idGranjero id del granjero por la que se realizara el filtrado.
     * @return Coleccion de contratos
     * @throws ClienteServidorConexionException
     * @throws BDServidorException
     */
    public Collection<ContratoEntity> getContratosGranjero(String idGranjero) throws ClienteServidorConexionException, BDServidorException;

    /**
     * Metodo para la recuperacion de un contrato
     *
     * @param idContrato id del contrato seleccionado
     * @return Objeto de tipo ContratoEntity
     * @throws ClienteServidorConexionException
     * @throws BDServidorException
     */
    public ContratoEntity getContrato(String idContrato) throws ClienteServidorConexionException, BDServidorException;

    /**
     * Metodo para la recuperacion de los contratos de un trabajador
     *
     * @param idTrabajador id del trabajador
     * @return Coleccion de contratos
     * @throws ClienteServidorConexionException
     * @throws BDServidorException
     */
    public Collection<ContratoEntity> getContratosTrabajador(String idTrabajador) throws ClienteServidorConexionException, BDServidorException;

    /**
     * Metodo para la recuperacion de los contratos en una granja
     *
     * @param idGranja
     * @return Coleccion de contratos
     * @throws ClienteServidorConexionException
     * @throws BDServidorException
     */
    public Collection<ContratoEntity> getContratosGranja(String idGranja) throws ClienteServidorConexionException, BDServidorException;

    /**
     * Metodo para el despido de un trabajador.
     *
     * @param idTrabajador id del trabajador a despedir
     * @param idGranja id de la granja en la que está contratado
     * @throws ClienteServidorConexionException
     * @throws BDServidorException
     */
    public void despedirTrabajador(String idTrabajador, String idGranja) throws ClienteServidorConexionException, BDServidorException;

    /**
     * Metodo para la modificacion del salario definido en un contrato.
     *
     * @param idTrabajador id del trabajador del contrato
     * @param idGranja id de la granja en la que está contratado.
     * @param salario Nuevo salario
     * @return Objecto de tipo ContratoEntity
     * @throws ClienteServidorConexionException
     * @throws BDServidorException
     */
    public ContratoEntity cambiarSueldo(String idTrabajador, String idGranja, String salario) throws ClienteServidorConexionException, BDServidorException;

    /**
     * Metodo para la creacion de un contrato.
     *
     * @param contrato
     * @throws ClienteServidorConexionException
     * @throws BDServidorException
     */
    public void contratarTrabajador(ContratoEntity contrato) throws ClienteServidorConexionException, BDServidorException;
}
