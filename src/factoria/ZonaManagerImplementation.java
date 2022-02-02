/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import clases.ContratoEntity;
import clases.ZonaEntity;
import interfaces.ZonaInterface;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.core.GenericType;
import restful.ZonaClient;

/**
 *
 * @author 2dam
 */
public class ZonaManagerImplementation implements ZonaInterface {

    private static ZonaClient webClient;

    public ZonaManagerImplementation() {
        this.webClient = new ZonaClient();
    }

    @Override
    public ZonaEntity getZona(String idZona) {
        ZonaEntity zona = null;
        zona = webClient.find(new GenericType<ZonaEntity>() {
        }, idZona);
        return zona;
    }

    @Override
    public Collection<ZonaEntity> getAllZonas() {
        List<ZonaEntity> zonas = null;

        zonas = webClient.findAll(new GenericType<List<ZonaEntity>>() {
        });

        return zonas;
    }

    @Override
    public ZonaEntity cambiarNombre(String nombreZona, String nombreZona1) {
        ZonaEntity zona = null;
        zona = webClient.cambiarNombreZona(new GenericType<ZonaEntity>() {
        }, nombreZona, nombreZona1);
        return zona;
    }

    @Override
    public void eliminarZona(Long idZona) {
        webClient.remove(idZona);
    }

    @Override
    public Collection<ZonaEntity> getZonasPorAnimal(String tipo) {
        List<ZonaEntity> zonas = null;
        zonas = webClient.zonasPorAnimal(new GenericType<List<ZonaEntity>>() {
        }, tipo);
        return zonas;
    }

    @Override
    public Collection<ZonaEntity> getZonasPorGranja(String id) {
        List<ZonaEntity> zonas = null;
        zonas = webClient.zonasPorGranja(new GenericType<List<ZonaEntity>>() {
        }, id);
        return zonas;
    }

    @Override
    public Collection<ZonaEntity> getZonasPorTrabajador(String username) {
        List<ZonaEntity> zonas = null;
        zonas = webClient.zonasPorTrabajador(new GenericType<List<ZonaEntity>>() {
        }, username);
        return zonas;
    }

    @Override
    public void crearZona(ZonaEntity zona) {
        System.out.println("creando server");

        webClient.create(zona);
    }

    @Override
    public void asignarTrabajador(String username, Long idZona) {
        webClient.asignarTrabajador(new GenericType<List<ZonaEntity>>(){}, username, idZona);
    }

    @Override
    public void desasignarTrabajador(String username, Long idZona) {
        webClient.quitarTrabajadorZona(new GenericType<List<ZonaEntity>>(){}, username, idZona);
    }

    @Override
    public void editarZona(ZonaEntity zona, String id) {
        webClient.edit(zona, id);
    }

}
