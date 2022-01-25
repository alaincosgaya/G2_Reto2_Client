/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import clases.ContratoEntity;
import clases.TrabajadorEntity;
import interfaces.ContratoInterface;
import interfaces.TrabajadorInterface;
import java.util.Collection;
import java.util.List;
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
    public Collection<TrabajadorEntity> getAllTrabajadores() {
        List<TrabajadorEntity> trabajadores = null;
        trabajadores = webClient.findAll(new GenericType<List<TrabajadorEntity>>() {
        });
        return trabajadores;
    }

    @Override
    public Collection<TrabajadorEntity> getTrabajadoresPorContratar(String idGranja) {
        List<TrabajadorEntity> trabajadores = null;
        trabajadores = webClient.trabajadoresParaContratar(new GenericType<List<TrabajadorEntity>>() {
        }, idGranja);
        return trabajadores;
    }

    @Override
    public TrabajadorEntity getTrabajador(String idTrabajador) {
        TrabajadorEntity trabajador = null;
        trabajador = webClient.find(new GenericType<TrabajadorEntity>() {
        }, idTrabajador);
        return trabajador;
    }

    @Override
    public Collection<TrabajadorEntity> getTrabajadoresGranja(String idGranja) {
        List<TrabajadorEntity> trabajadores = null;
        trabajadores = webClient.trabajadoresGranja(new GenericType<List<TrabajadorEntity>>() {
        }, idGranja);
        return trabajadores;
    }

    @Override
    public Collection<TrabajadorEntity> getTrabajadoresZona(String idZona) {
        List<TrabajadorEntity> trabajadores = null;
        trabajadores = webClient.trabajadoresZona(new GenericType<List<TrabajadorEntity>>() {
        }, idZona);
        return trabajadores;
    }

    @Override
    public Collection<TrabajadorEntity> getTrabajadoresPorAsignarZona(Long idZona, Long idGranja) {
        List<TrabajadorEntity> trabajadores = null;
        trabajadores = webClient.trabajadoresAsignarZona(new GenericType<List<TrabajadorEntity>>() {
        }, idGranja, idZona);
        return trabajadores;

    }

    @Override
    public void Registro() {

    }

}
