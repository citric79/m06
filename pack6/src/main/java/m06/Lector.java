package m06;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// Esta clase representa a un lector
@Entity
@Table(name = "lectores")
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "edad")
    private int edad;

    @OneToMany(mappedBy = "lector", cascade = CascadeType.ALL)
    private List<Prestamo> prestamos;

    // Constructor vacío
    public Lector() {
        prestamos = new ArrayList<>();
    }

    // Constructor con todos los atributos
    public Lector(String nombre, String apellido, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        prestamos = new ArrayList<>();
    }

    // Getters y setters para todos los atributos

    // Devuelve el ID del lector
    public int getId() {
        return id;
    }

    // Establece el ID del lector
    public void setId(int id) {
        this.id = id;
    }

    // Devuelve el nombre del lector
    public String getNombre() {
        return nombre;
    }

    // Establece el nombre del lector
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Devuelve el apellido del lector
    public String getApellido() {
        return apellido;
    }

    // Establece el apellido del lector
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    // Devuelve la edad del lector
    public int getEdad() {
        return edad;
    }

    // Establece la edad del lector
    public void setEdad(int edad) {
        this.edad = edad;
    }

    // Devuelve la lista de préstamos del lector
    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    // Establece la lista de préstamos del lector
    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    // Representación en cadena del lector
    @Override
    public String toString() {
        return "ID: " + getId() + ", Nombre: " + getNombre() + ", Apellido: " + getApellido() + ", Edad: " + getEdad();
    }
}
