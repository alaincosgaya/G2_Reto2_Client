/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import clases.ZonaEntity;
import java.util.Collection;

/**
 *
 * @author 2dam
 */
public interface ZonaInterface {

    public ZonaEntity getZona(String id);

    public Collection<ZonaEntity> getAllZonas();

    public ZonaEntity cambiarNombre(String nombreZona, String nombreZona1);
    
    public void editarZona(ZonaEntity zona, String id);

    public void eliminarZona(Long idZona);

    public Collection<ZonaEntity> getZonasPorAnimal(String tipo);
    
    public Collection<ZonaEntity> getZonasPorGranja(String id);
    
    public Collection<ZonaEntity> getZonasPorTrabajador(String username);

    public void crearZona(ZonaEntity zona);

    public void asignarTrabajador(String username, Long idZona);
    
    public void desasignarTrabajador(String username, Long idZona);
}
