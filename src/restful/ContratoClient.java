/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:ContratoEntityFacadeREST
 * [entidades.contratoentity]<br>
 * USAGE:
 * <pre>
 *        ContratoClient client = new ContratoClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre> Jersey de la clase ContratoEntity
 *
 * @author Alain Cosgaya
 */
public class ContratoClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("archivos.config")
            .getString("restfulURI");

    /**
     * Constructor del Jersey
     */
    public ContratoClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entidades.contratoentity");
    }

    /**
     * Metodo para la recuperacion de contratos del granjero
     *
     * @param <T>
     * @param responseType
     * @param idGranjero
     * @return Coleccion generica
     * @throws ClientErrorException
     */
    public <T> T contratosGranjero(GenericType<T> responseType, String idGranjero) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("contratosGranjero/{0}", new Object[]{idGranjero}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Metodo para la edicion de contratos.
     *
     * @param requestEntity
     * @param id
     * @throws ClientErrorException
     */
    public void edit(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Metodo para la recuperacion de un contrato
     *
     * @param <T>
     * @param responseType
     * @param id
     * @return
     * @throws ClientErrorException
     */
    public <T> T find(GenericType<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Metodo para la creacion de un contrato en formato XML
     *
     * @param requestEntity
     * @throws ClientErrorException
     */
    public void create_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Metodo para la creacion de un contrato en formato JSON
     *
     * @param requestEntity
     * @throws ClientErrorException
     */
    public void create_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Metodo para la recuperacion de contratos de un trabajador
     *
     * @param <T>
     * @param responseType
     * @param idTrabajador
     * @return
     * @throws ClientErrorException
     */
    public <T> T contratosTrabajador(GenericType<T> responseType, String idTrabajador) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("contratosTrabajador/{0}", new Object[]{idTrabajador}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Metodo para la recuperacion de todos los contratos.
     * @param <T>
     * @param responseType
     * @return Coleccion de contratos
     * @throws ClientErrorException
     */
    public <T> T findAll(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Metodo para el borrado de un contrato.
     * @param id
     * @throws ClientErrorException 
     */
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Metodo para el despido de un trabajador.
     * @param idTrabajador
     * @param idGranja
     * @throws ClientErrorException 
     */
    public void despedirTrabajador(String idTrabajador, String idGranja) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("despedir/{0}/{1}", new Object[]{idTrabajador, idGranja})).request().delete();
    }

    /**
     * Metodo para la modificacion de un salario definido en un contrato.
     * @param <T>
     * @param responseType
     * @param idTrabajador
     * @param idGranja
     * @param salario
     * @return
     * @throws ClientErrorException 
     */
    public <T> T cambiarSueldo(GenericType<T> responseType, String idTrabajador, String idGranja, String salario) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("cambiarSueldo/{0}/{1}/{2}", new Object[]{idTrabajador, idGranja, salario}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Metodo para la recuperacion de los contratos de una granja.
     * @param <T>
     * @param responseType
     * @param idGranja
     * @return Coleccion de contratos
     * @throws ClientErrorException 
     */
    public <T> T contratosGranja(GenericType<T> responseType, String idGranja) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("contratosGranja/{0}", new Object[]{idGranja}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void close() {
        client.close();
    }

}
