/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jonathan Camacho y Alain Cosgaya
 */
@XmlRootElement()
public class ContratoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private SimpleObjectProperty<ContratoId> idContrato;
   
    private SimpleObjectProperty<TrabajadorEntity> trabajador; 
    
    private SimpleObjectProperty<GranjaEntity> granja;
   
    private SimpleObjectProperty<Date> fechaContratacion;

    private SimpleLongProperty salario;

    public Long getSalario() {
        return this.salario.get();
    }

    public void setSalario(Long salario) {
        this.salario.set(salario);
    }

      public ContratoEntity() {
        this.idContrato = new SimpleObjectProperty();
        this.trabajador = new SimpleObjectProperty();
        this.granja = new SimpleObjectProperty();
       
        this.fechaContratacion = new SimpleObjectProperty();
        this.salario = new SimpleLongProperty();
    }

    
     public ContratoEntity(ContratoId idContrato, TrabajadorEntity trabajador, GranjaEntity granja, Date fechaContratacion, Long salario) {
        this.idContrato = new SimpleObjectProperty(idContrato);
        this.trabajador = new SimpleObjectProperty(trabajador);
        this.granja = new SimpleObjectProperty(granja);
       
        this.fechaContratacion = new SimpleObjectProperty(fechaContratacion);
        this.salario = new SimpleLongProperty(salario);
    }
    

    
    public ContratoId getIdContrato() {
        return (ContratoId) this.idContrato.get();
    }

    public void setIdContrato(ContratoId idContrato) {
        this.idContrato.set(idContrato);
    }

    public TrabajadorEntity getTrabajador() {
        return this.trabajador.get();
    }

    public void setTrabajador(TrabajadorEntity trabajador) {
        this.trabajador.set(trabajador);
    }

    public GranjaEntity getGranja() {
        return this.granja.get();
    }

    public void setGranja(GranjaEntity granja) {
        this.granja.set(granja);
    }
    
    
    public Date getFechaContratacion() {
        return this.fechaContratacion.get();
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion.set(fechaContratacion);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.idContrato);
        hash = 67 * hash + Objects.hashCode(this.trabajador);
        hash = 67 * hash + Objects.hashCode(this.granja);
        hash = 67 * hash + Objects.hashCode(this.fechaContratacion);
        hash = 67 * hash + Objects.hashCode(this.salario);
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
        final ContratoEntity other = (ContratoEntity) obj;
        if (!Objects.equals(this.idContrato, other.idContrato)) {
            return false;
        }
        if (!Objects.equals(this.trabajador, other.trabajador)) {
            return false;
        }
        if (!Objects.equals(this.granja, other.granja)) {
            return false;
        }
        if (!Objects.equals(this.fechaContratacion, other.fechaContratacion)) {
            return false;
        }
        if (!Objects.equals(this.salario, other.salario)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ContratoEntity{" + "idContrato=" + idContrato + ", trabajador=" + trabajador + ", granja=" + granja + ", fechaContratacion=" + fechaContratacion + ", salario=" + salario + '}';
    }

}
