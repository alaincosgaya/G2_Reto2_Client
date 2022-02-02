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
import javax.ws.rs.core.GenericType;
import restful.GranjaClient;

/**
 *
 * @author Alejandro Gomez
 */
public class GranjaManagerImplementation implements GranjaInterface {

    private static GranjaClient webClient;

    public GranjaManagerImplementation() {
        this.webClient = new GranjaClient();
    }

    @Override
    public void crearGranja(GranjaEntity granja) {
        webClient.create(granja);
    }

    @Override
    public Collection<GranjaEntity> getAllGranjas() throws ClienteServidorConexionException, BDServidorException{
        List<GranjaEntity> granjas = null;
        granjas = webClient.findAll(new GenericType<List<GranjaEntity>>() {
        });
        return granjas;
    }

    @Override
    public GranjaEntity getGranja(String idGranja) throws ClienteServidorConexionException, BDServidorException{
        GranjaEntity granja = null;
        granja = webClient.find(new GenericType<GranjaEntity>() {
        }, idGranja);
        return granja;
    }

    @Override
    public GranjaEntity getGranjaPorNombre(String nombreGranja) throws ClienteServidorConexionException, BDServidorException{
        GranjaEntity granja = null;
        granja = webClient.granjaPorNombre(new GenericType<GranjaEntity>() {
        }, nombreGranja);
        return granja;
    }

    @Override
    public GranjaEntity getGranjaPorzona(String idZona) throws ClienteServidorConexionException, BDServidorException{
        GranjaEntity granja = null;
        granja = webClient.granjaALaQuePerteneceEsazona(new GenericType<GranjaEntity>() {
        }, idZona);
        return granja;
    }

    @Override
    public Collection<GranjaEntity> getGranjasPorGranjero(String username) throws ClienteServidorConexionException, BDServidorException{
        List<GranjaEntity> granjas = null;
        granjas = webClient.granjasPorLoginDelGranjero(new GenericType<List<GranjaEntity>>() {
        }, username);
        return granjas;
    }

    @Override
    public Collection<GranjaEntity> getGranjasPorTrabajador(String username) throws ClienteServidorConexionException, BDServidorException{
        List<GranjaEntity> granjas = null;
        granjas = webClient.granjasPorLoginDelGranjero(new GenericType<List<GranjaEntity>>() {
        }, username);
        return granjas;
    }

    @Override
    public GranjaEntity cambiarNombreDeLaGranja(Long idGranja, String nombreGranja) throws ClienteServidorConexionException, BDServidorException{
        GranjaEntity granja = null;
        granja = webClient.updateNombreDeLaGranja(new GenericType<GranjaEntity>() {
        }, idGranja, nombreGranja);
        return granja;
    }
    
    @Override
    public GranjaEntity cambiarFechaCreacionDeLaGranja(Long idGranja, Date fechaCreacion) throws ClienteServidorConexionException, BDServidorException{
        GranjaEntity granja = null;
        granja = webClient.updateFechaCreacionDeLaGranja(new GenericType<GranjaEntity>() {
        }, idGranja, fechaCreacion);
        return granja;
    }

    @Override
    public void editarGranja(GranjaEntity granja) throws ClienteServidorConexionException, BDServidorException{
        webClient.edit(granja, granja.getIdGranja());    
    }

    @Override
    public void borrarGranja(String idGranja) throws ClienteServidorConexionException, BDServidorException{
        webClient.deleteGranja(idGranja);
    }

}
