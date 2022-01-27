package clases;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JavaBean de
 *
 * @author Jonathan Camacho
 */
@XmlRootElement()
public class AnimalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

   /* private Long idAnimal;
    private String nombreAnimal;
    private TipoAnimal tipo;
    private EstadoAnimal estado;
    private Date fechaNacimiento;
    private SexoAnimal sexo;
    private Zona zona;

    public Long getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(Long idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getNombreAnimal() {
        return nombreAnimal;
    }

    public void setNombreAnimal(String nombreAnimal) {
        this.nombreAnimal = nombreAnimal;
    }

    public TipoAnimal getTipo() {
        return tipo;
    }

    public void setTipo(TipoAnimal tipo) {
        this.tipo = tipo;
    }

    public EstadoAnimal getEstado() {
        return estado;
    }

    public void setEstado(EstadoAnimal estado) {
        this.estado = estado;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public SexoAnimal getSexo() {
        return sexo;
    }

    public void setSexo(SexoAnimal sexo) {
        this.sexo = sexo;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }*/

    
    private SimpleLongProperty idAnimal;
    private SimpleStringProperty nombreAnimal;
    private SimpleObjectProperty<TipoAnimal> tipo;
    private SimpleObjectProperty<EstadoAnimal> estado;
    private SimpleObjectProperty<Date> fechaNacimiento;
    private SimpleObjectProperty<SexoAnimal> sexo;
    private SimpleObjectProperty<Zona> zona;

    //constructor publico vacio
    public AnimalEntity() {
        this.idAnimal = new SimpleLongProperty();
        this.nombreAnimal = new SimpleStringProperty();
        this.tipo = new SimpleObjectProperty();
        this.estado = new SimpleObjectProperty();
        this.fechaNacimiento = new SimpleObjectProperty();
        this.sexo = new SimpleObjectProperty();
        this.zona = new SimpleObjectProperty();
    }

    //constructor con parametros
    public AnimalEntity(Long idAnimal, String nombreAnimal, TipoAnimal tipo, EstadoAnimal estado, Date fechaNacimiento, SexoAnimal sexo, Zona zona) {
        this.idAnimal = new SimpleLongProperty(idAnimal);
        this.nombreAnimal = new SimpleStringProperty(nombreAnimal);
        this.tipo = new SimpleObjectProperty(tipo);
        this.estado = new SimpleObjectProperty(estado);
        this.fechaNacimiento = new SimpleObjectProperty(fechaNacimiento);
        this.sexo = new SimpleObjectProperty(sexo);
        this.zona = new SimpleObjectProperty(zona);
    }

    //getters and setters
    public Long getIdAnimal() {
        return this.idAnimal.get();
    }

    public void setIdAnimal(Long idAnimal) {
        this.idAnimal.set(idAnimal);
    }

    public String getNombreAnimal() {
        return this.nombreAnimal.get();
    }

    public void setNombreAnimal(String nombreAnimal) {
        this.nombreAnimal.set(nombreAnimal);
    }

    public TipoAnimal getTipo() {
        return this.tipo.get();
    }

    public void setTipo(TipoAnimal tipo) {
        this.tipo.set(tipo);
    }

    public EstadoAnimal getEstado() {
        return this.estado.get();
    }

    public void setEstado(EstadoAnimal estado) {
        this.estado.set(estado);
    }

    public Date getFechaNacimiento() {
        return this.fechaNacimiento.get();
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento.set(fechaNacimiento);
    }

    public SexoAnimal getSexo() {
        return this.sexo.get();
    }

    public void setSexo(SexoAnimal sexo) {
        this.sexo.set(sexo);
    }

    public Zona getZona() {
        return this.zona.get();
    }

    public void setZona(Zona zona) {
        this.zona.set(zona);
    }
     
    //metodos
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idAnimal);
        hash = 67 * hash + Objects.hashCode(this.nombreAnimal);
        hash = 67 * hash + Objects.hashCode(this.tipo);
        hash = 67 * hash + Objects.hashCode(this.estado);
        hash = 67 * hash + Objects.hashCode(this.fechaNacimiento);
        hash = 67 * hash + Objects.hashCode(this.sexo);
        hash = 67 * hash + Objects.hashCode(this.zona);
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
        final AnimalEntity other = (AnimalEntity) obj;
        if (!Objects.equals(this.nombreAnimal, other.nombreAnimal)) {
            return false;
        }
        if (!Objects.equals(this.idAnimal, other.idAnimal)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.fechaNacimiento, other.fechaNacimiento)) {
            return false;
        }
        if (!Objects.equals(this.sexo, other.sexo)) {
            return false;
        }
        if (!Objects.equals(this.zona, other.zona)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Animal{" + "idAnimal=" + idAnimal + ", nombreAnimel=" + nombreAnimal + ", tipo=" + tipo + ", estado=" + estado + ", fechaNacimiento=" + fechaNacimiento + ", sexoAnimal=" + sexo + ", zona=" + zona + '}';
    }

}
