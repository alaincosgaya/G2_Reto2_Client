/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import clases.AnimalEntity;
import clases.EstadoAnimal;
import clases.SexoAnimal;
import clases.TipoAnimal;
import clases.Zona;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Jonathan Camacho
 */
public interface InterfazAnimal {

    //creacion de animales
    public void crearAnimal(AnimalEntity animal);

    //Diferentes listados de animales
    public Collection<AnimalEntity> getAllAnimales();

    public Collection<AnimalEntity> getAnimalesPorTipo(String tipo);

    public Collection<AnimalEntity> getAnimalesPorEstado(String estado);

    public Collection<AnimalEntity> getAnimalesPorSexo(String sexo);

    //listado de las zonas, necesario para cargar los datos en la comboBox
    //public Collection<Zona> getAllZonas();

    //Modificaciones disponibles para los animales
    public void cambiarNombreAnimal(Long idAnimal, String nombreAnimal);

    public void cambiarEstadoAnimal(Long idAnimal, String estado);

    public void cambiarSexoAnimal(Long idAnimal, String sexo);

    public void cambiarTipoAnimal(Long idAnimal, String tipo);

    public void cambiarFechaAnimal(Long idAnimal, String fecha);

    public void cambiarZonaAnimal(Long idAnimal, Zona zona);

    //Eliminacion de un animal
    public void eliminarAnimal(Long idAnimal);
    
    public void editarAnimal(AnimalEntity animal);

}
