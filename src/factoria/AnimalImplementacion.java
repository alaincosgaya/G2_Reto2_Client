/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import clases.AnimalEntity;
import clases.Zona;
import interfaces.InterfazAnimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.GenericType;
import restful.AnimalClient;

/**
 *
 * @author Jonathan Camacho
 */
public class AnimalImplementacion implements InterfazAnimal {

    private static AnimalClient webCliente;

    public AnimalImplementacion() {
        this.webCliente = new AnimalClient();
    }

    @Override
    public void crearAnimal(AnimalEntity animal) {
        webCliente.create(animal);
    }

    @Override
    public Collection<AnimalEntity> getAllAnimales() {
        List<AnimalEntity> animales = null;
        animales = webCliente.findAll(new GenericType<List<AnimalEntity>>() {
        });
        return animales;
    }

    @Override
    public Collection<AnimalEntity> getAnimalesPorTipo(String tipo) {
        List<AnimalEntity> animales = null;
        animales = webCliente.animalesPorTipo(new GenericType<List<AnimalEntity>>() {
        }, tipo);
        return animales;
    }

    @Override
    public Collection<AnimalEntity> getAnimalesPorEstado(String estado) {
        List<AnimalEntity> animales = null;
        animales = webCliente.animalesPorEstado(new GenericType<List<AnimalEntity>>() {
        }, estado);
        return animales;
    }

    @Override
    public Collection<AnimalEntity> getAnimalesPorSexo(String sexo) {
        List<AnimalEntity> animales = null;
        animales = webCliente.animalesPorSexo(new GenericType<List<AnimalEntity>>() {
        }, sexo);
        return animales;
    }

    @Override
    public void eliminarAnimal(Long idAnimal) {
        webCliente.remove(idAnimal);
    }

    @Override
    public void cambiarEstadoAnimal(Long idAnimal, String estado) {
        webCliente.cambiarEstadoAnimal(new GenericType<List<AnimalEntity>>() {
        }, idAnimal, estado);

    }

    @Override
    public void cambiarSexoAnimal(Long idAnimal, String sexo) {
        webCliente.cambiarSexoAnimal(new GenericType<List<AnimalEntity>>() {
        }, idAnimal, sexo);
    }

    @Override
    public void cambiarTipoAnimal(Long idAnimal, String tipo) {
        webCliente.cambiarTipoAnimal(new GenericType<List<AnimalEntity>>() {
        }, idAnimal, tipo);
    }

    @Override
    public void cambiarNombreAnimal(Long idAnimal, String nombreAnimal) {
        webCliente.cambiarNombreAnimal(new GenericType<List<AnimalEntity>>() {
        }, idAnimal, nombreAnimal);
    }

    @Override
    public void cambiarFechaAnimal(Long idAnimal, String fecha) {
        webCliente.cambiarFechaAnimal(new GenericType<List<AnimalEntity>>() {
        }, idAnimal, fecha);
    }

    @Override
    public void cambiarZonaAnimal(Long idAnimal, Zona zona) {
        webCliente.cambiarZonaAnimal(new GenericType<List<AnimalEntity>>() {
        }, idAnimal, zona);
    }

    @Override
    public void editarAnimal(AnimalEntity animal) {
        webCliente.edit(animal, animal.getIdAnimal());
    }


}
