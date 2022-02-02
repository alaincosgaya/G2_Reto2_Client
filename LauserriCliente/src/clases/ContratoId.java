/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.Objects;


/**
 *
 * @author Jonathan Camacho
 */

public class ContratoId implements Serializable{
    private Long trabajadorId;
    private Long granjaId;

    public Long getTrabajadorId() {
        return trabajadorId;
    }

    public void setTrabajadorId(Long trabajadorId) {
        this.trabajadorId = trabajadorId;
    }

    public Long getGranjaId() {
        return granjaId;
    }

    public void setGranjaId(Long granjaId) {
        this.granjaId = granjaId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.trabajadorId);
        hash = 97 * hash + Objects.hashCode(this.granjaId);
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
        final ContratoId other = (ContratoId) obj;
        if (!Objects.equals(this.trabajadorId, other.trabajadorId)) {
            return false;
        }
        if (!Objects.equals(this.granjaId, other.granjaId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ContratoId{" + "trabajadorId=" + trabajadorId + ", granjaId=" + granjaId + '}';
    }
    
    
}
