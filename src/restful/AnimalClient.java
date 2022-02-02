/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import clases.ZonaEntity;
import java.util.Date;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:AnimalEntityFacadeREST
 * [entidades.animalentity]<br>
 * USAGE:
 * <pre>
 *        AnimalClient client = new AnimalClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Jonathan Camacho
 */
public class AnimalClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("archivos.config")
            .getString("restfulURI");

    /**
     * Constructor de AnimalClient.
     */
    public AnimalClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entidades.animalentity");
    }

    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Metodo con el cual listaremos los animales por su estado.
     *
     * @param <T>
     * @param responseType
     * @param estado
     * @return Una lista de animales
     * @throws ClientErrorException
     */
    public <T> T animalesPorEstado(GenericType<T> responseType, String estado) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("estadoAnimal/{0}", new Object[]{estado}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Metodo con el cual se realizan modificaciones en el servidor.
     *
     * @param requestEntity
     * @param id
     * @throws ClientErrorException
     */
    public void edit(Object requestEntity, Long id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * metodo que permite la modificacion del estado del animal.
     *
     * @param <T>
     * @param responseType
     * @param idAnimal
     * @param estado
     * @return Un tipo de dato generico
     * @throws ClientErrorException
     */
    public <T> T cambiarEstadoAnimal(GenericType<T> responseType, Long idAnimal, String estado) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("cambiarEstadoAnimal/{0}/{1}", new Object[]{idAnimal, estado}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * metodo que permite la modificacion del sexo del animal.
     *
     * @param <T>
     * @param responseType
     * @param idAnimal
     * @param sexo
     * @return Un tipo de dato generico
     * @throws ClientErrorException
     */
    public <T> T cambiarSexoAnimal(GenericType<T> responseType, Long idAnimal, String sexo) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("cambiarSexoAnimal/{0}/{1}", new Object[]{idAnimal, sexo}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * metodo que permite la modificacion del tipo del animal.
     *
     * @param <T>
     * @param responseType
     * @param idAnimal
     * @param tipo
     * @return Un tipo de dato generico
     * @throws ClientErrorException
     */
    public <T> T cambiarTipoAnimal(GenericType<T> responseType, Long idAnimal, String tipo) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("cambiarTipoAnimal/{0}/{1}", new Object[]{idAnimal, tipo}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * metodo que permite la modificacion del nombre del animal.
     *
     * @param <T>
     * @param responseType
     * @param idAnimal
     * @param nombreAnimal
     * @return Un tipo de dato generico
     * @throws ClientErrorException
     */
    public <T> T cambiarNombreAnimal(GenericType<T> responseType, Long idAnimal, String nombreAnimal) throws ClientErrorException {

        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("cambiarNombreAnimal/{0}/{1}", new Object[]{idAnimal, nombreAnimal}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);

    }

    /**
     * Metodo con el cual se puede seleccionar a un animal segun su id.
     *
     * @param <T>
     * @param responseType
     * @param id
     * @return El objeto seleccionado
     * @throws ClientErrorException
     */
    public <T> T find(GenericType<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Metodo con el cual se puede listar a los animales existentes en un rango.
     *
     * @param <T>
     * @param responseType
     * @param from
     * @param to
     * @return Una lista de animales
     * @throws ClientErrorException
     */
    public <T> T findRange(GenericType<T> responseType, String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Metodo por el cual se pueden listar los animales segun su sexo.
     *
     * @param <T>
     * @param responseType
     * @param sexo
     * @return Una lista de animales
     * @throws ClientErrorException
     */
    public <T> T animalesPorSexo(GenericType<T> responseType, String sexo) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("animalSexo/{0}", new Object[]{sexo}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Metodo con el cual se procede a crear a un animal.
     *
     * @param requestEntity
     * @throws ClientErrorException
     */
    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Metodo por el cual se pueden listar los animales segun su tipo.
     *
     * @param <T>
     * @param responseType
     * @param tipo
     * @return Una lista de animales
     * @throws ClientErrorException
     */
    public <T> T animalesPorTipo(GenericType<T> responseType, String tipo) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("TipoAnimal/{0}", new Object[]{tipo}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Metodo con el cual se realiza la busqueda de todos los animales
     * actualmente introducidos en la base de datos
     *
     * @param <T>
     * @param responseType
     * @return Una lista de animales
     * @throws ClientErrorException
     */
    public <T> T findAll(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Metodo con el cual se procede a eliminar a un animal.
     *
     * @param id
     * @throws ClientErrorException
     */
    public void remove(Long id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Metodo por el cual se realiza el cambio de nombre de los animales.
     *
     * @param <T>
     * @param responseType
     * @param nombreAnimal
     * @return Un tipo de dato generico
     * @throws ClientErrorException
     */
    public <T> T animalesPorNombre(GenericType<T> responseType, String nombreAnimal) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("nombreAnimal/{0}", new Object[]{nombreAnimal}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Cierra el servicio web del RESTful.
     */
    public void close() {
        client.close();
    }

}
