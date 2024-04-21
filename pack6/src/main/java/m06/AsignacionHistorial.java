package m06;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Esta clase representa el historial de asignación de libros a lectores
@Entity
@Table(name = "asignacion_historial")
public class AsignacionHistorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "id_lector")
    private int idLector;

    @Column(name = "id_libro")
    private int idLibro;

    @Column(name = "fecha_asignacion")
    private LocalDateTime fechaAsignacion;

    public AsignacionHistorial() {
    }

    // Constructor con todos los atributos
    public AsignacionHistorial(int id, int idLector, int idLibro, LocalDateTime fechaAsignacion) {
        this.id = id;
        this.idLector = idLector;
        this.idLibro = idLibro;
        this.fechaAsignacion = fechaAsignacion;
    }

    // Getters y setters para todos los atributos

    // Devuelve el ID de la asignación
    public int getId() {
        return id;
    }

    // Establece el ID de la asignación
    public void setId(int id) {
        this.id = id;
    }

    // Devuelve el ID del lector
    public int getIdLector() {
        return idLector;
    }

    // Establece el ID del lector
    public void setIdLector(int idLector) {
        this.idLector = idLector;
    }

    // Devuelve el ID del libro
    public int getIdLibro() {
        return idLibro;
    }

    // Establece el ID del libro
    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    // Devuelve la fecha de asignación
    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    // Establece la fecha de asignación
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    // Representación en cadena de la asignación histórica
    @Override
    public String toString() {
        return "AsignacionHistorial{" +
                "id=" + id +
                ", idLector=" + idLector +
                ", idLibro=" + idLibro +
                ", fechaAsignacion=" + fechaAsignacion +
                '}';
    }
}
