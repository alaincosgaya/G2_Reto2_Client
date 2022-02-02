package factoria;

import clases.AnimalEntity;
import clases.ZonaEntity;
import excepciones.BDServidorException;
import excepciones.ClienteServidorConexionException;
import interfaces.InterfazAnimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.AnimalClient;

/**
 * Clase con los metodos que se ejecutarán en la aplicacion. Esta implementacion
 * esta llamando a metodos de la clase restful para realizar las operaciones
 *
 * @author Jonathan Camacho
 */
public class AnimalImplementacion implements InterfazAnimal {

    private static AnimalClient webCliente;

    /**
     * Metodo con el cual se implementarán los metodos de la clase restful.
     * AnimalClient
     */
    public AnimalImplementacion() {
        this.webCliente = new AnimalClient();
    }

    /**
     * Metodo con el cual se procede a crear al animal en la base de datos.
     *
     * @param animal
     * @throws excepciones.ClienteServidorConexionException
     * @throws excepciones.BDServidorException
     */
    @Override
    public void crearAnimal(AnimalEntity animal) throws ClienteServidorConexionException, BDServidorException {
        try {
            webCliente.create(animal);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error a la hora de ejecutar el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse con el servidor");
        }
    }

    /**
     * Metodo con el cual se listan todos los animales.
     *
     * @return animales
     * @throws excepciones.ClienteServidorConexionException
     * @throws excepciones.BDServidorException
     */
    @Override
    public Collection<AnimalEntity> getAllAnimales() throws ClienteServidorConexionException, BDServidorException {
        List<AnimalEntity> animales = null;
        try {
            animales = webCliente.findAll(new GenericType<List<AnimalEntity>>() {
            });
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error a la hora de ejecutar el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse con el servidor");
        }
        return animales;
    }

    /**
     * Metodo con el cual se listan todos los animales por tipo.
     *
     * @param tipo
     * @return animales
     * @throws excepciones.ClienteServidorConexionException
     * @throws excepciones.BDServidorException
     */
    @Override
    public Collection<AnimalEntity> getAnimalesPorTipo(String tipo) throws ClienteServidorConexionException, BDServidorException {
        List<AnimalEntity> animales = null;
        try {
            animales = webCliente.animalesPorTipo(new GenericType<List<AnimalEntity>>() {
            }, tipo);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error a la hora de ejecutar el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse con el servidor");
        }

        return animales;
    }

    /**
     * Metodo con el cual se listan todos los animales por estado.
     *
     * @param estado
     * @return animales
     * @throws excepciones.ClienteServidorConexionException
     * @throws excepciones.BDServidorException
     */
    @Override
    public Collection<AnimalEntity> getAnimalesPorEstado(String estado) throws ClienteServidorConexionException, BDServidorException {
        List<AnimalEntity> animales = null;
        try {
            animales = webCliente.animalesPorEstado(new GenericType<List<AnimalEntity>>() {
            }, estado);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error a la hora de ejecutar el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse con el servidor");
        }
        return animales;
    }

    /**
     * Metodo con el cual se listan todos los animales segun su sexo.
     *
     * @param sexo
     * @return animales
     * @throws excepciones.ClienteServidorConexionException
     * @throws excepciones.BDServidorException
     */
    @Override
    public Collection<AnimalEntity> getAnimalesPorSexo(String sexo) throws ClienteServidorConexionException, BDServidorException {
        List<AnimalEntity> animales = null;
        try {
            animales = webCliente.animalesPorSexo(new GenericType<List<AnimalEntity>>() {
            }, sexo);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error a la hora de ejecutar el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse con el servidor");
        }
        return animales;
    }

    /**
     * Metodo con el cual se procede a eliminar a un animal.
     *
     * @param idAnimal
     * @throws excepciones.ClienteServidorConexionException
     * @throws excepciones.BDServidorException
     */
    @Override
    public void eliminarAnimal(Long idAnimal) throws ClienteServidorConexionException, BDServidorException {
        try {
            webCliente.remove(idAnimal);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error a la hora de ejecutar el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse con el servidor");
        }
    }

    /**
     * Metodo con el cual se cambia el estado del animal.
     *
     * @param idAnimal
     * @param estado
     * @throws excepciones.ClienteServidorConexionException
     */
    @Override

    public void cambiarEstadoAnimal(Long idAnimal, String estado) throws ClienteServidorConexionException, BDServidorException {
        try {
            webCliente.cambiarEstadoAnimal(new GenericType<List<AnimalEntity>>() {
            }, idAnimal, estado);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error a la hora de ejecutar el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse con el servidor");
        }

    }

    /**
     * Metodo con el cual se cambia el sexo del animal.
     *
     * @param idAnimal
     * @param sexo
     * @throws excepciones.ClienteServidorConexionException
     * @throws excepciones.BDServidorException
     */
    @Override
    public void cambiarSexoAnimal(Long idAnimal, String sexo) throws ClienteServidorConexionException, BDServidorException {
        try {
            webCliente.cambiarSexoAnimal(new GenericType<List<AnimalEntity>>() {
            }, idAnimal, sexo);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error a la hora de ejecutar el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse con el servidor");
        }
    }

    /**
     * Metodo con el cual se cambia el tipo de animal.
     *
     * @param idAnimal
     * @param tipo
     * @throws excepciones.ClienteServidorConexionException
     * @throws excepciones.BDServidorException
     */
    @Override
    public void cambiarTipoAnimal(Long idAnimal, String tipo) throws ClienteServidorConexionException, BDServidorException {
        try {
            webCliente.cambiarTipoAnimal(new GenericType<List<AnimalEntity>>() {
            }, idAnimal, tipo);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error a la hora de ejecutar el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse con el servidor");
        }
    }

    /**
     * Metodo con el cual se cambia el nombre del animal.
     *
     * @param idAnimal
     * @param nombreAnimal
     * @throws excepciones.ClienteServidorConexionException
     * @throws excepciones.BDServidorException
     */
    @Override
    public void cambiarNombreAnimal(Long idAnimal, String nombreAnimal) throws ClienteServidorConexionException, BDServidorException {
        try {
            webCliente.cambiarNombreAnimal(new GenericType<List<AnimalEntity>>() {
            }, idAnimal, nombreAnimal);
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error a la hora de ejecutar el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse con el servidor");
        }

    }

    /**
     * Metodo con el cual se edita a los animales, utilizado para cambiar la
     * fecha y la zona a la que pertenece.
     *
     * @param animal
     * @throws excepciones.ClienteServidorConexionException
     * @throws excepciones.BDServidorException
     */
    @Override
    public void editarAnimal(AnimalEntity animal) throws ClienteServidorConexionException, BDServidorException {
        try {
            webCliente.edit(animal, animal.getIdAnimal());
        } catch (ClientErrorException ex) {
            throw new BDServidorException("Error a la hora de ejecutar el servidor");
        } catch (Exception ex) {
            throw new ClienteServidorConexionException("Error a la hora de conectarse con el servidor");
        }
    }

}
