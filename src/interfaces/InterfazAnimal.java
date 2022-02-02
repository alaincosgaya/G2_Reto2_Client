package interfaces;

import clases.AnimalEntity;
import clases.EstadoAnimal;
import clases.SexoAnimal;
import clases.TipoAnimal;
import clases.ZonaEntity;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import java.util.Collection;
import java.util.Date;
import javax.ws.rs.ClientErrorException;

/**
 * Interfaz la cual implementa los metodos de la clase AnimalImplementacion.
 *
 * @author Jonathan Camacho
 */
public interface InterfazAnimal {

    //creacion de animales
    public void crearAnimal(AnimalEntity animal) throws ClienteServidorConexionException, BDServidorException;

    //Diferentes listados de animales
    public Collection<AnimalEntity> getAllAnimales() throws ClienteServidorConexionException, BDServidorException;

    public Collection<AnimalEntity> getAnimalesPorTipo(String tipo) throws ClienteServidorConexionException, BDServidorException;

    public Collection<AnimalEntity> getAnimalesPorEstado(String estado) throws ClienteServidorConexionException, BDServidorException;

    public Collection<AnimalEntity> getAnimalesPorSexo(String sexo) throws ClienteServidorConexionException, BDServidorException;

    //Modificaciones disponibles para los animales
    public void cambiarNombreAnimal(Long idAnimal, String nombreAnimal) throws ClienteServidorConexionException, BDServidorException;

    public void cambiarEstadoAnimal(Long idAnimal, String estado) throws ClienteServidorConexionException, BDServidorException;

    public void cambiarSexoAnimal(Long idAnimal, String sexo) throws ClienteServidorConexionException, BDServidorException;

    public void cambiarTipoAnimal(Long idAnimal, String tipo) throws ClienteServidorConexionException, BDServidorException;

    public void editarAnimal(AnimalEntity animal) throws ClienteServidorConexionException, BDServidorException;

    //Eliminacion de un animal
    public void eliminarAnimal(Long idAnimal) throws ClienteServidorConexionException, BDServidorException;

}
