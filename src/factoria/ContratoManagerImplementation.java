/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import clases.ContratoEntity;
import clases.ContratoId;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import interfaces.ContratoInterface;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.ContratoClient;

/**
 * Implementacion de la interfaz de los contratos
 *
 * @author Alain Cosgaya
 */
public class ContratoManagerImplementation implements ContratoInterface {

    private static final Logger LOGGER = Logger.getLogger(ContratoManagerImplementation.class.getName());

    private static ContratoClient webClient;

    /**
     * Constructor de la implementacion.
     */
    public ContratoManagerImplementation() {
        this.webClient = new ContratoClient();
    }

    /**
     * Metodo para la recuperación de todos los contratos.
     *
     * @return Coleccion de contratos.
     * @throws ClienteServidorConexionException
     * @throws BDServidorException
     */
    @Override
    public Collection<ContratoEntity> getAllContratos() throws ClienteServidorConexionException, BDServidorException {
        List<ContratoEntity> contratos = null;
        try {
            LOGGER.log(Level.INFO, "Iniciando busqueda de contratos");
            contratos = webClient.findAll(new GenericType<List<ContratoEntity>>() {
            });
            LOGGER.log(Level.INFO, "Contratos recuperados.");
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return contratos;
    }

    /**
     * Metodo para la recuperacion de los contratos realizados por un granjero
     *
     * @param idGranjero id del granjero por la que se realizara el filtrado.
     * @return Coleccion de contratos
     * @throws ClienteServidorConexionException
     * @throws BDServidorException
     */
    @Override
    public Collection<ContratoEntity> getContratosGranjero(String idGranjero) throws ClienteServidorConexionException, BDServidorException {
        List<ContratoEntity> contratos = null;
        try {
            LOGGER.log(Level.INFO, "Iniciando busqueda de contratos");
            contratos = webClient.contratosGranjero(new GenericType<List<ContratoEntity>>() {
            }, idGranjero);
            LOGGER.log(Level.INFO, "Contratos recuperados.");
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return contratos;
    }

    /**
     * Metodo para la recuperacion de un contrato
     *
     * @param idContrato id del contrato seleccionado
     * @return Objeto de tipo ContratoEntity
     * @throws ClienteServidorConexionException
     * @throws BDServidorException
     */
    @Override
    public ContratoEntity getContrato(ContratoId idContrato) throws ClienteServidorConexionException, BDServidorException {
        ContratoEntity contrato = null;
        try {
            LOGGER.log(Level.INFO, "Iniciando busqueda de contrato");
            contrato = webClient.find(new GenericType<ContratoEntity>() {
            }, idContrato);
            LOGGER.log(Level.INFO, "Contratos recuperado.");
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return contrato;
    }

    /**
     * Metodo para la recuperacion de los contratos de un trabajador
     *
     * @param idTrabajador id del trabajador
     * @return Coleccion de contratos
     * @throws ClienteServidorConexionException
     * @throws BDServidorException
     */
    @Override
    public Collection<ContratoEntity> getContratosTrabajador(String idTrabajador) throws ClienteServidorConexionException, BDServidorException {
        List<ContratoEntity> contratos = null;
        try {
            LOGGER.log(Level.INFO, "Iniciando busqueda de contratos");
            contratos = webClient.contratosTrabajador(new GenericType<List<ContratoEntity>>() {
            }, idTrabajador);
            LOGGER.log(Level.INFO, "Contratos recuperados.");
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return contratos;
    }

    /**
     * Metodo para la recuperacion de los contratos en una granja
     *
     * @param idGranja
     * @return Coleccion de contratos
     * @throws ClienteServidorConexionException
     * @throws BDServidorException
     */
    @Override
    public Collection<ContratoEntity> getContratosGranja(String idGranja) throws ClienteServidorConexionException, BDServidorException {
        List<ContratoEntity> contratos = null;
        try {
            LOGGER.log(Level.INFO, "Iniciando busqueda de contratos");
            contratos = webClient.contratosGranja(new GenericType<List<ContratoEntity>>() {
            }, idGranja);
            LOGGER.log(Level.INFO, "Contratos recuperados.");
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return contratos;
    }

    /**
     * Metodo para el despido de un trabajador.
     *
     * @param idTrabajador id del trabajador a despedir
     * @param idGranja id de la granja en la que está contratado
     * @throws ClienteServidorConexionException
     * @throws BDServidorException
     */
    @Override
    public void despedirTrabajador(String idTrabajador, String idGranja) throws ClienteServidorConexionException, BDServidorException {
        try {
            LOGGER.log(Level.INFO, "Despidiendo trabajador.");
            webClient.despedirTrabajador(idTrabajador, idGranja);
            LOGGER.log(Level.INFO, "Despido completado.");
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
    }

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
    @Override
    public ContratoEntity cambiarSueldo(String idTrabajador, String idGranja, String salario) throws ClienteServidorConexionException, BDServidorException {
        ContratoEntity contrato = null;
        try {
            LOGGER.log(Level.INFO, "Modificando salario.");
            contrato = webClient.cambiarSueldo(new GenericType<ContratoEntity>() {
            }, idTrabajador, idGranja, salario);
            LOGGER.log(Level.INFO, "Salario modificado.");
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return contrato;
    }

    /**
     * Metodo para la creacion de un contrato.
     *
     * @param contrato
     * @throws ClienteServidorConexionException
     * @throws BDServidorException
     */
    @Override
    public void contratarTrabajador(ContratoEntity contrato) throws ClienteServidorConexionException, BDServidorException {
        try {
            LOGGER.log(Level.INFO, "Creando contrato.");
            webClient.create_XML(contrato);
            LOGGER.log(Level.INFO, "Trabajador contratado.");
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }

    }

}
