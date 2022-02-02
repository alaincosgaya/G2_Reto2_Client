/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import clases.ContratoEntity;
import clases.TrabajadorEntity;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import java.util.Collection;

/**
 *
 * @author 2dam
 */
public interface TrabajadorInterface {

    public Collection<TrabajadorEntity> getAllTrabajadores() throws ClienteServidorConexionException, BDServidorException ;

    public Collection<TrabajadorEntity> getTrabajadoresPorContratar(String idGranja) throws ClienteServidorConexionException, BDServidorException ;

    public Collection<TrabajadorEntity> getTrabajadoresGranja(String idGranja) throws ClienteServidorConexionException, BDServidorException ;

    public Collection<TrabajadorEntity> getTrabajadoresZona(String idZona) throws ClienteServidorConexionException, BDServidorException ;

    public Collection<TrabajadorEntity> getTrabajadoresPorAsignarZona(Long idZona,Long idGranja) throws ClienteServidorConexionException, BDServidorException ;
    
    public TrabajadorEntity getTrabajador(String idTrabajador) throws ClienteServidorConexionException, BDServidorException ;
    
    public void Registro();
}
