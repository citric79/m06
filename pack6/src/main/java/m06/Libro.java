package m06;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// Representa un libro en la biblioteca
@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "autor")
    private String autor;

    @Column(name = "anio_publicacion")
    private int añoPublicacion;

    @Column(name = "disponible")
    private boolean disponible;

    @ManyToOne
    @JoinColumn(name = "lector_id")
    private Lector lector;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
    private List<Prestamo> prestamos;

    
    public Libro() {
        this.titulo = "";
        this.autor = "";
        this.añoPublicacion = 0;
        this.disponible = true;
        this.prestamos = new ArrayList<>();
    }

    // Constructor con todos los atributos
    public Libro(String titulo, String autor, int añoPublicacion, boolean disponible) {
        this.titulo = titulo;
        this.autor = autor;
        this.añoPublicacion = añoPublicacion;
        this.disponible = disponible;
        this.prestamos = new ArrayList<>();
    }

    // Getters y setters para todos los atributos

    // Devuelve el ID del libro
    public int getId() {
        return id;
    }

    // Establece el ID del libro
    public void setId(int id) {
        this.id = id;
    }

    // Devuelve el título del libro
    public String getTitulo() {
        return titulo;
    }

    // Establece el título del libro
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Devuelve el autor del libro
    public String getAutor() {
        return autor;
    }

    // Establece el autor del libro
    public void setAutor(String autor) {
        this.autor = autor;
    }

    // Devuelve el año de publicación del libro
    public int getAñoPublicacion() {
        return añoPublicacion;
    }

    // Establece el año de publicación del libro
    public void setAñoPublicacion(int añoPublicacion) {
        this.añoPublicacion = añoPublicacion;
    }

    // Devuelve el estado de disponibilidad del libro
    public boolean isDisponible() {
        return disponible;
    }

    // Establece el estado de disponibilidad del libro
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // Devuelve el lector que tiene prestado el libro
    public Lector getLector() {
        return lector;
    }

    // Establece el lector que tiene prestado el libro
    public void setLector(Lector lector) {
        this.lector = lector;
    }

    // Devuelve la lista de préstamos del libro
    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    // Establece la lista de préstamos del libro
    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    // Verifica si el libro está disponible para ser prestado
    public boolean isDisponibleParaPrestamo() {
        return disponible;
    }

    // Establece el estado de disponibilidad del libro para préstamo
    public void setDisponibleParaPrestamo(boolean disponible) {
        this.disponible = disponible;
    }

    // Representación en cadena del libro
    @Override
    public String toString() {
        return "ID: " + getId() + ", Título: " + getTitulo() + ", Autor: " + getAutor() + ", Año de Publicación: " + getAñoPublicacion() + ", Disponible: " + isDisponible();
    }
}
