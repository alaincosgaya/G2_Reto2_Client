/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import clases.ContratoEntity;
import clases.TrabajadorEntity;
import java.util.Collection;

/**
 *
 * @author 2dam
 */
public interface TrabajadorInterface {

    public Collection<TrabajadorEntity> getAllTrabajadores();

    public Collection<TrabajadorEntity> getTrabajadoresPorContratar(String idGranja);

    public Collection<TrabajadorEntity> getTrabajadoresGranja(String idGranja);

    public Collection<TrabajadorEntity> getTrabajadoresZona(String idZona);

    public Collection<TrabajadorEntity> getTrabajadoresPorAsignarZona(Long idZona,Long idGranja);
    
    public TrabajadorEntity getTrabajador(String idTrabajador);
    
    public void Registro();
}
