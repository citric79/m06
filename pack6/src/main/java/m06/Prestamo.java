package m06;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// Representa un préstamo de un libro a un lector
@Entity
@Table(name = "prestamos")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lector_id")
    private Lector lector;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "libro_id")
    private Libro libro;
    
    
    public Prestamo() {}

    // Constructor con todos los atributos
    public Prestamo(int id, Lector lector, Libro libro) {
        this.id = id;
        this.lector = lector;
        this.libro = libro;
        // Marcar el libro como no disponible para préstamo
        if (libro != null) {
            libro.setDisponibleParaPrestamo(false);
        }
    }

    // Getters y setters para todos los atributos

    // Devuelve el ID del préstamo
    public int getId() {
        return id;
    }

    // Establece el ID del préstamo
    public void setId(int id) {
        this.id = id;
    }

    // Devuelve el lector que realizó el préstamo
    public Lector getLector() {
        return lector;
    }

    // Establece el lector que realizó el préstamo
    public void setLector(Lector lector) {
        this.lector = lector;
    }

    // Devuelve el libro prestado
    public Libro getLibro() {
        return libro;
    }

    // Establece el libro prestado y marca el libro como no disponible para préstamo
    public void setLibro(Libro libro) {
        this.libro = libro;
        if (libro != null) {
            libro.setDisponibleParaPrestamo(false);
        }
    }

    // Representación en cadena del préstamo
    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", lector=" + lector +
                ", libro=" + libro +
                '}';
    }
}
