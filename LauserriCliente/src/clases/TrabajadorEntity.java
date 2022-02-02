/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;
    
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alain Cosgaya
 */

@XmlRootElement()
public class TrabajadorEntity extends UserEntity implements Serializable {

    private static final Long serialVersionUID = 1L; 
   
    private List<ContratoEntity> contratos; 
    
 
    private List<ZonaEntity> zonas;


    public List<ContratoEntity> getContratos() {
        return contratos;
    }
    
    public void setGranjas(List<ContratoEntity> contratos) {
        this.contratos = contratos;
    }

    @XmlTransient
    public List<ZonaEntity> getZonas() {
        return zonas;
    }

    public void setZonas(List<ZonaEntity> zonas) {
        this.zonas = zonas;
    }

    public TrabajadorEntity() {
    
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.contratos);
        hash = 59 * hash + Objects.hashCode(this.zonas);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TrabajadorEntity other = (TrabajadorEntity) obj;
        
        if (!Objects.equals(this.contratos, other.contratos)) {
            return false;
        }
        if (!Objects.equals(this.zonas, other.zonas)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.getUsername();
    }
    
    

   
}
