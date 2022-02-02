/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alejandro Gómez
 */
@XmlRootElement()
public class GranjaEntity implements Serializable {

    private static final Long serialVersionUID = 1L;
    private SimpleLongProperty idGranja;
    private SimpleStringProperty nombreGranja;
    private SimpleObjectProperty<Date> fechaCreacion;
    private SimpleObjectProperty<GranjeroEntity> granjero;
    private SimpleListProperty<Zona> zonas;
    private SimpleListProperty<ContratoEntity> contratos;

    //Constructores
    public GranjaEntity() {
        this.idGranja = new SimpleLongProperty();
        this.nombreGranja = new SimpleStringProperty();
        this.fechaCreacion = new SimpleObjectProperty();
        this.granjero = new SimpleObjectProperty();
        this.zonas = new SimpleListProperty();
        this.contratos = new SimpleListProperty();
    }

    public GranjaEntity(Long idGranja, String nombreGranja, Date fechaCreacion, GranjeroEntity granjero, List<Zona> zonas, List<ContratoEntity> contratos) {
        this.idGranja = new SimpleLongProperty(idGranja);
        this.nombreGranja = new SimpleStringProperty(nombreGranja);
        this.fechaCreacion = new SimpleObjectProperty(fechaCreacion);
        this.granjero = new SimpleObjectProperty(granjero);
        this.zonas = new SimpleListProperty((ObservableList) zonas);
        this.contratos = new SimpleListProperty((ObservableList) contratos);
    }

    //Getters and Setters
    public Long getIdGranja() {
        return this.idGranja.get();
    }

    public void setIdGranja(Long idGranja) {
        this.idGranja.set(idGranja);
    }

    public String getNombreGranja() {
        return this.nombreGranja.get();
    }

    public void setNombreGranja(String nombreGranja) {
        this.nombreGranja.set(nombreGranja);
    }

    public Date getFechaCreacion() {
        return this.fechaCreacion.get();
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion.set(fechaCreacion);
    }

    public GranjeroEntity getGranjero() {
        return this.granjero.get();
    }

    public void setGranjero(GranjeroEntity granjero) {
        this.granjero.set(granjero);
    }

    @XmlTransient
    public ObservableList<Zona> getZonas() {
        return this.zonas.get();
    }

    public void setZonas(ObservableList<Zona> zonas) {
        this.zonas.set(zonas);
    }

    @XmlTransient
    public ObservableList<ContratoEntity> getContratos() {
        return this.contratos.get();
    }

    public void setContratos(ObservableList<ContratoEntity> contratos) {
        this.contratos.set(contratos);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.idGranja);
        hash = 37 * hash + Objects.hashCode(this.nombreGranja);
        hash = 37 * hash + Objects.hashCode(this.fechaCreacion);
        hash = 37 * hash + Objects.hashCode(this.granjero);
        hash = 37 * hash + Objects.hashCode(this.zonas);
        hash = 37 * hash + Objects.hashCode(this.contratos);
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
        final GranjaEntity other = (GranjaEntity) obj;
        if (!Objects.equals(this.idGranja, other.idGranja)) {
            return false;
        }
        if (!Objects.equals(this.nombreGranja, other.nombreGranja)) {
            return false;
        }
        if (!Objects.equals(this.fechaCreacion, other.fechaCreacion)) {
            return false;
        }
        if (!Objects.equals(this.granjero, other.granjero)) {
            return false;
        }
        if (!Objects.equals(this.zonas, other.zonas)) {
            return false;
        }
        if (!Objects.equals(this.contratos, other.contratos)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getNombreGranja();

    }
}
