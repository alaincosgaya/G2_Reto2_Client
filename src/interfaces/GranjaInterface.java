/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import clases.GranjaEntity;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Alejandro Gomez
 */
public interface GranjaInterface {
    
    public void crearGranja(GranjaEntity granja);
    
    public Collection<GranjaEntity> getAllGranjas()throws ClienteServidorConexionException, BDServidorException;
    
    public GranjaEntity getGranja(String idGranja)throws ClienteServidorConexionException, BDServidorException;
    
    public GranjaEntity getGranjaPorNombre(String nombreGranja)throws ClienteServidorConexionException, BDServidorException;
    
    public GranjaEntity getGranjaPorzona(String idZona)throws ClienteServidorConexionException, BDServidorException;
    
    public Collection<GranjaEntity> getGranjasPorGranjero(String username)throws ClienteServidorConexionException, BDServidorException;
    
    public Collection<GranjaEntity> getGranjasPorTrabajador(String username)throws ClienteServidorConexionException, BDServidorException;
    
    public GranjaEntity cambiarNombreDeLaGranja(Long idGranja, String nombreGranja)throws ClienteServidorConexionException, BDServidorException;
    
    public GranjaEntity cambiarFechaCreacionDeLaGranja(Long idGranja, Date fechaCreacion)throws ClienteServidorConexionException, BDServidorException;
    
    public void borrarGranja(String idGranja)throws ClienteServidorConexionException, BDServidorException;

    public void editarGranja(GranjaEntity granja)throws ClienteServidorConexionException, BDServidorException;
}
