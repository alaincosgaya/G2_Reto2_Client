/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import clases.GranjaEntity;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import interfaces.GranjaInterface;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.GranjaClient;

/**
 *
 * @author Alejandro
 */
public class GranjaManagerImplementation implements GranjaInterface {

    private static GranjaClient webClient;

    public GranjaManagerImplementation() {
        this.webClient = new GranjaClient();
    }

    @Override
    public void crearGranja(GranjaEntity granja) throws ClienteServidorConexionException, BDServidorException {
        try {
            webClient.create(granja);
        } catch (ClientErrorException ex) {
            throw new ClienteServidorConexionException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new BDServidorException("Error a la hora de conectarse al servidor");
        }
    }

    @Override
    public Collection<GranjaEntity> getAllGranjas() throws ClienteServidorConexionException, BDServidorException {

        List<GranjaEntity> granjas = null;
        try {
            granjas = webClient.findAll(new GenericType<List<GranjaEntity>>() {
            });
        } catch (ClientErrorException ex) {
            throw new ClienteServidorConexionException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new BDServidorException("Error a la hora de conectarse al servidor");
        }
        return granjas;
    }

    @Override
    public GranjaEntity getGranja(String idGranja) throws ClienteServidorConexionException, BDServidorException {

        GranjaEntity granja = null;
        try {
            granja = webClient.find(new GenericType<GranjaEntity>() {
            }, idGranja);
        } catch (ClientErrorException ex) {
            throw new ClienteServidorConexionException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new BDServidorException("Error a la hora de conectarse al servidor");
        }
        return granja;
    }

    @Override
    public GranjaEntity getGranjaPorNombre(String nombreGranja)  throws ClienteServidorConexionException, BDServidorException  {
        GranjaEntity granja = null;
        try {
            granja = webClient.granjaPorNombre(new GenericType<GranjaEntity>() {
            }, nombreGranja);
        } catch (ClientErrorException ex) {
            throw new ClienteServidorConexionException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new BDServidorException("Error a la hora de conectarse al servidor");
        }
        return granja;
    }

    @Override
    public GranjaEntity getGranjaPorzona(String idZona)  throws ClienteServidorConexionException, BDServidorException {
        GranjaEntity granja = null;
        try {
            granja = webClient.granjaALaQuePerteneceEsazona(new GenericType<GranjaEntity>() {
            }, idZona);
        } catch (ClientErrorException ex) {
            throw new ClienteServidorConexionException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new BDServidorException("Error a la hora de conectarse al servidor");
        }
        return granja;
    }

    @Override
    public Collection<GranjaEntity> getGranjasPorGranjero(String username) throws ClienteServidorConexionException, BDServidorException  {
        List<GranjaEntity> granjas = null;
        try {
            granjas = webClient.granjasPorLoginDelGranjero(new GenericType<List<GranjaEntity>>() {
            }, username);
        } catch (ClientErrorException ex) {
            throw new ClienteServidorConexionException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new BDServidorException("Error a la hora de conectarse al servidor");
        }
        return granjas;
    }

    @Override
    public Collection<GranjaEntity> getGranjasPorTrabajador(String username) throws ClienteServidorConexionException, BDServidorException  {
        List<GranjaEntity> granjas = null;
        try {
            granjas = webClient.granjasPorLoginDelGranjero(new GenericType<List<GranjaEntity>>() {
            }, username);
        } catch (ClientErrorException ex) {
            throw new ClienteServidorConexionException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new BDServidorException("Error a la hora de conectarse al servidor");
        }
        return granjas;
    }

    @Override
    public GranjaEntity cambiarNombreDeLaGranja(Long idGranja, String nombreGranja) throws ClienteServidorConexionException, BDServidorException  {
        GranjaEntity granja = null;
        try {
            granja = webClient.updateNombreDeLaGranja(new GenericType<GranjaEntity>() {
            }, idGranja, nombreGranja);
        } catch (ClientErrorException ex) {
            throw new ClienteServidorConexionException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new BDServidorException("Error a la hora de conectarse al servidor");
        }
        return granja;
    }

    @Override
    public void borrarGranja(String idGranja) throws ClienteServidorConexionException, BDServidorException  {
        try {
            webClient.deleteGranja(idGranja);
        } catch (ClientErrorException ex) {
            throw new ClienteServidorConexionException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new BDServidorException("Error a la hora de conectarse al servidor");
        }
    }

    @Override
    public GranjaEntity cambiarFechaCreacionDeLaGranja(Long idGranja, Date fechaCreacion) throws ClienteServidorConexionException, BDServidorException  {
        GranjaEntity granja = null;
        try {
            granja = webClient.updateFechaCreacionDeLaGranja(new GenericType<GranjaEntity>() {
            }, idGranja, fechaCreacion);
        } catch (ClientErrorException ex) {
            throw new ClienteServidorConexionException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new BDServidorException("Error a la hora de conectarse al servidor");
        }
        return granja;
    }

    @Override
    public Collection<GranjaEntity> getGranjasNoTrabajador(String username) throws ClienteServidorConexionException, BDServidorException  {
        List<GranjaEntity> granjas = null;
        try {
            granjas = webClient.GranjasEnLasQueNoTrabajaEseTrabajador(new GenericType<List<GranjaEntity>>() {
            }, username);
        } catch (ClientErrorException ex) {
            throw new ClienteServidorConexionException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new BDServidorException("Error a la hora de conectarse al servidor");
        }
        return granjas;
    }

    @Override
    public void editarGranja(GranjaEntity granja) throws ClienteServidorConexionException, BDServidorException  {
        try {
            webClient.edit(granja, granja.getIdGranja());
        } catch (ClientErrorException ex) {
            throw new ClienteServidorConexionException("Error ejecutando el servidor");
        } catch (Exception ex) {
            throw new BDServidorException("Error a la hora de conectarse al servidor");
        }
    }

}
