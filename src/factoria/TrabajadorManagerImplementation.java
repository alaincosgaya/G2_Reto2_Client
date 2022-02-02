/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import clases.ContratoEntity;
import clases.TrabajadorEntity;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import interfaces.ContratoInterface;
import interfaces.TrabajadorInterface;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.ContratoClient;
import restful.TrabajadorClient;

/**
 *
 * @author 2dam
 */
public class TrabajadorManagerImplementation implements TrabajadorInterface {

    private static TrabajadorClient webClient;

    public TrabajadorManagerImplementation() {
        this.webClient = new TrabajadorClient();
    }

    @Override
    public Collection<TrabajadorEntity> getAllTrabajadores()  throws ClienteServidorConexionException, BDServidorException {

        List<TrabajadorEntity> trabajadores = null;
        try {
            trabajadores = webClient.findAll(new GenericType<List<TrabajadorEntity>>() {
            });

        } catch (ClientErrorException ex) {
            throw new ClienteServidorConexionException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new BDServidorException("Error a la hora de conectarse al servidor");
        }
        return trabajadores;
    }

    @Override
    public Collection<TrabajadorEntity> getTrabajadoresPorContratar(String idGranja)  throws ClienteServidorConexionException, BDServidorException {

        List<TrabajadorEntity> trabajadores = null;
        try {
            trabajadores = webClient.trabajadoresParaContratar(new GenericType<List<TrabajadorEntity>>() {
            }, idGranja);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return trabajadores;
    }

    @Override
    public TrabajadorEntity getTrabajador(String idTrabajador) throws ClienteServidorConexionException, BDServidorException  {
        TrabajadorEntity trabajador = null;
        try {
            trabajador = webClient.find(new GenericType<TrabajadorEntity>() {
            }, idTrabajador);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return trabajador;
    }

    @Override
    public Collection<TrabajadorEntity> getTrabajadoresGranja(String idGranja) throws ClienteServidorConexionException, BDServidorException  {
        List<TrabajadorEntity> trabajadores = null;
        try {
            trabajadores = webClient.trabajadoresGranja(new GenericType<List<TrabajadorEntity>>() {
            }, idGranja);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return trabajadores;
    }

    @Override
    public Collection<TrabajadorEntity> getTrabajadoresZona(String idZona) throws ClienteServidorConexionException, BDServidorException  {
        List<TrabajadorEntity> trabajadores = null;
        try {
            trabajadores = webClient.trabajadoresZona(new GenericType<List<TrabajadorEntity>>() {
            }, idZona);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return trabajadores;
    }

    @Override
    public Collection<TrabajadorEntity> getTrabajadoresPorAsignarZona(Long idZona, Long idGranja)  throws ClienteServidorConexionException, BDServidorException  {
        List<TrabajadorEntity> trabajadores = null;
        try{
        trabajadores = webClient.trabajadoresAsignarZona(new GenericType<List<TrabajadorEntity>>() {
        }, idGranja, idZona);
          } catch (ClientErrorException ex) {
            throw new BDServidorException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse al servidor");
        }
        return trabajadores;

    }

    @Override
    public void Registro() {

    }

}
