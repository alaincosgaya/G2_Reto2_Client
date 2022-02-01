/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import clases.ContratoEntity;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import interfaces.ContratoInterface;
import java.net.ConnectException;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.ContratoClient;

/**
 *
 * @author 2dam
 */
public class ContratoManagerImplementation implements ContratoInterface {

    private static ContratoClient webClient;

    public ContratoManagerImplementation() {
        this.webClient = new ContratoClient();
    }

    @Override
    public Collection<ContratoEntity> getAllContratos() throws ClienteServidorConexionException, BDServidorException {
        List<ContratoEntity> contratos = null;
        try {
            contratos = webClient.findAll(new GenericType<List<ContratoEntity>>() {
            });
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return contratos;
    }

    @Override
    public Collection<ContratoEntity> getContratosGranjero(String idGranjero) throws ClienteServidorConexionException, BDServidorException {
        List<ContratoEntity> contratos = null;
        try {
            contratos = webClient.contratosGranjero(new GenericType<List<ContratoEntity>>() {
            }, idGranjero);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return contratos;
    }

    @Override
    public ContratoEntity getContrato(String idContrato) throws ClienteServidorConexionException, BDServidorException {
        ContratoEntity contrato = null;
        try {
            contrato = webClient.find(new GenericType<ContratoEntity>() {
            }, idContrato);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return contrato;
    }

    @Override
    public Collection<ContratoEntity> getContratosTrabajador(String idTrabajador) throws ClienteServidorConexionException, BDServidorException {
        List<ContratoEntity> contratos = null;
        try {
            contratos = webClient.contratosTrabajador(new GenericType<List<ContratoEntity>>() {
            }, idTrabajador);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return contratos;
    }

    @Override
    public Collection<ContratoEntity> getContratosGranja(String idGranja) throws ClienteServidorConexionException, BDServidorException {
        List<ContratoEntity> contratos = null;
        try {
            contratos = webClient.contratosGranja(new GenericType<List<ContratoEntity>>() {
            }, idGranja);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return contratos;
    }

    @Override
    public void despedirTrabajador(String idTrabajador, String idGranja) throws ClienteServidorConexionException, BDServidorException {
        try {
            webClient.despedirTrabajador(idTrabajador, idGranja);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
    }

    @Override
    public ContratoEntity cambiarSueldo(String idTrabajador, String idGranja, String salario) throws ClienteServidorConexionException, BDServidorException {
        ContratoEntity contrato = null;
        try {
            contrato = webClient.cambiarSueldo(new GenericType<ContratoEntity>() {
            }, idTrabajador, idGranja, salario);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return contrato;
    }

    @Override
    public void contratarTrabajador(ContratoEntity contrato) throws ClienteServidorConexionException, BDServidorException {
        try {
            webClient.create_XML(contrato);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }

    }

}
