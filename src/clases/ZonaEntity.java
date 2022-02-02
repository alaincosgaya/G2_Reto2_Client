package clases;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Idoia Ormaetxea
 */

@XmlRootElement
public class ZonaEntity implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private SimpleLongProperty idZona;
    private SimpleStringProperty nombreZona;
    
    private SimpleObjectProperty<Date> fechaCreacionZona;
    
    private SimpleObjectProperty<GranjaEntity>  granja;
    
    private SimpleObjectProperty<List<AnimalEntity>> animales;
    
    private SimpleObjectProperty<List<TrabajadorEntity>>  trabajadores;
    
    public ZonaEntity(){
        this.idZona = new SimpleLongProperty();
        this.nombreZona = new SimpleStringProperty();
        this.fechaCreacionZona = new SimpleObjectProperty();
        this.granja = new SimpleObjectProperty();
        this.animales = new SimpleObjectProperty();
        this.trabajadores = new SimpleObjectProperty();
    }

    public ZonaEntity(Long idZona, String nombreZona, Date fechaCreacionZona, GranjaEntity granja, AnimalEntity animales, TrabajadorEntity trabajadores) {
        this.idZona = new SimpleLongProperty(idZona);
        this.nombreZona = new SimpleStringProperty(nombreZona);
        this.fechaCreacionZona = new SimpleObjectProperty(fechaCreacionZona);
        this.granja = new SimpleObjectProperty(granja);
        this.animales = new SimpleObjectProperty(animales);
        this.trabajadores = new SimpleObjectProperty(trabajadores);
    }
    
    

    public Long getIdZona() {
        return this.idZona.get();
    }

    public void setIdZona(Long idZona) {
        this.idZona.set(idZona);
    }

    public String getNombreZona() {
        return this.nombreZona.get();
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona.set(nombreZona);
    }

    public Date getFechaCreacionZona() {
        return this.fechaCreacionZona.get();
    }

    public void setFechaCreacionZona(Date fechaCreacionZona) {
        this.fechaCreacionZona.set(fechaCreacionZona);
    }

    public GranjaEntity getGranja() {
        return this.granja.get();
    }

    public void setGranja(GranjaEntity granja) {
        this.granja.set(granja);
    }

    @XmlTransient
    public List<AnimalEntity> getAnimales() {
        return this.animales.get();
    }

    public void setAnimales(List<AnimalEntity> animales) {
        this.animales.set(animales);
    }

    @XmlTransient
    public List<TrabajadorEntity> getTrabajadores() {
        return this.trabajadores.get();
    }

    public void setTrabajadores(List<TrabajadorEntity> trabajadores) {
        this.trabajadores.set(trabajadores);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.idZona);
        hash = 29 * hash + Objects.hashCode(this.nombreZona);
        hash = 29 * hash + Objects.hashCode(this.fechaCreacionZona);
        hash = 29 * hash + Objects.hashCode(this.granja);
        hash = 29 * hash + Objects.hashCode(this.animales);
        hash = 29 * hash + Objects.hashCode(this.trabajadores);
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
        final ZonaEntity other = (ZonaEntity) obj;
        if (!Objects.equals(this.nombreZona, other.nombreZona)) {
            return false;
        }
        if (!Objects.equals(this.idZona, other.idZona)) {
            return false;
        }
        if (!Objects.equals(this.fechaCreacionZona, other.fechaCreacionZona)) {
            return false;
        }
        if (!Objects.equals(this.granja, other.granja)) {
            return false;
        }
        if (!Objects.equals(this.animales, other.animales)) {
            return false;
        }
        if (!Objects.equals(this.trabajadores, other.trabajadores)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "Zona{" + "idZona=" + idZona + ", nombreZona=" + nombreZona + ", fechaCreacionZona=" + fechaCreacionZona + ", granja=" + granja + ", animales=" + animales + ", trabajadores=" + trabajadores + '}';
        return this.getNombreZona();
    }
    
    
}
