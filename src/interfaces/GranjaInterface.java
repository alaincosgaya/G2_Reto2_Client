/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import clases.GranjaEntity;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Alejandro Gomez
 */
public interface GranjaInterface {
    
    public void crearGranja(GranjaEntity granja);
    
    public Collection<GranjaEntity> getAllGranjas();
    
    public GranjaEntity getGranja(String idGranja);
    
    public GranjaEntity getGranjaPorNombre(String nombreGranja);
    
    public GranjaEntity getGranjaPorzona(String idZona);
    
    public Collection<GranjaEntity> getGranjasPorGranjero(String username);
    
    public Collection<GranjaEntity> getGranjasPorTrabajador(String username);
    
    public GranjaEntity cambiarNombreDeLaGranja(Long idGranja, String nombreGranja);
    
    public GranjaEntity cambiarFechaCreacionDeLaGranja(Long idGranja, Date fechaCreacion);
    
    public void borrarGranja(String idGranja);

    public void editarGranja(GranjaEntity granja);
}
