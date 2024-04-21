package m06;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class Main {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Configuracion de Hibernate
        sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Libro.class)
                .addAnnotatedClass(Lector.class)
                .addAnnotatedClass(Prestamo.class)
                .addAnnotatedClass(AsignacionHistorial.class) //
                .buildSessionFactory();
        
        // Mostramos el menú
        mostrarMenu(scanner);

        // Cerramos el scanner y la sesión de Hibernate
        scanner.close();
        sessionFactory.close();
    }

    private static void mostrarMenu(Scanner scanner) {
        int opcion;
        do {
            System.out.println("\nMenú:");
            System.out.println("1. Insertar libro");
            System.out.println("2. Insertar lector");
            System.out.println("3. Actualizar libro");
            System.out.println("4. Actualizar lector");
            System.out.println("5. Borrar libro");
            System.out.println("6. Borrar lector");
            System.out.println("7. Asignar libro a lector");
            System.out.println("8. Registrar devolución de libro");
            System.out.println("9. Listado de libros");
            System.out.println("10. Listado de lectores");
            System.out.println("11. Ver libro por ID");
            System.out.println("12. Ver lector por ID");
            System.out.println("13. Consultar libros prestados a un lector");
            System.out.println("14. Consultar libros disponibles para préstamo");
            System.out.println("15. Consultar historial de préstamos por lector");
            System.out.println("16. Salir");
            System.out.print("Por favor, seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    insertarLibro(scanner);
                    break;
                case 2:
                    insertarLector(scanner);
                    break;
                case 3:
                    actualizarLibro(scanner);
                    break;
                case 4:
                    actualizarLector(scanner);
                    break;
                case 5:
                    borrarLibro(scanner);
                    break;
                case 6:
                    borrarLector(scanner);
                    break;
                case 7:
                    asignarLibroALector(scanner);
                    break;
                case 8:
                    registrarDevolucion(scanner);
                    break;
                case 9:
                    listarLibros();
                    break;
                case 10:
                    listarLectores();
                    break;
                case 11:
                    verLibroPorId(scanner);
                    break;
                case 12:
                    verLectorPorId(scanner);
                    break;
                case 13:
                	consultarLibrosPrestados(scanner);
                    break;
                case 14:
                    consultarLibrosDisponibles();
                    break;

                case 15:
                    consultarHistorialPrestamosPorLector(scanner);
                    break;
                case 16:
                    System.out.println("¡Hasta luego!");
                    break;
                    
                    
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción del menú.");
                    break;
            }
        } while (opcion != 16);
    
		
	}

	private static void insertarLibro(Scanner scanner) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            System.out.println("Ingrese el título del libro:");
            String titulo = scanner.nextLine();

            System.out.println("Ingrese el autor del libro:");
            String autor = scanner.nextLine();

            System.out.println("Ingrese el año de publicación del libro:");
            int añoPublicacion = scanner.nextInt();

            scanner.nextLine(); // Limpiar el buffer

            // Solicitar al usuario que indique si el libro está disponible para préstamo
            System.out.println("¿El libro está disponible para préstamo? (Sí/No)");
            boolean disponibleParaPrestamo = scanner.nextLine().equalsIgnoreCase("Sí");

            // Crear una instancia de Libro con los datos proporcionados por el usuario
            Libro nuevoLibro = new Libro(titulo, autor, añoPublicacion, disponibleParaPrestamo);

            // Guardar el nuevo libro en la base de datos
            session.save(nuevoLibro);

            transaction.commit();
            System.out.println("Libro insertado correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void insertarLector(Scanner scanner) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            System.out.println("Ingrese el nombre del lector:");
            String nombre = scanner.nextLine();

            System.out.println("Ingrese el apellido del lector:");
            String apellido = scanner.nextLine();

            System.out.println("Ingrese la edad del lector:");
            int edad = scanner.nextInt();

            scanner.nextLine(); // Limpiar el buffer

            // Crear una instancia de Lector
            Lector lector = new Lector();
            lector.setNombre(nombre);
            lector.setApellido(apellido);
            lector.setEdad(edad);

            // Guardar el lector en la base de datos
            session.save(lector);

            transaction.commit();
            System.out.println("Lector insertado correctamente en la base de datos.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void actualizarLibro(Scanner scanner) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            System.out.println("Ingrese el ID del libro que desea actualizar:");
            int id = scanner.nextInt();

            Libro libro = session.get(Libro.class, id);
            if (libro != null) {
                System.out.println("Ingrese el nuevo título del libro:");
                scanner.nextLine(); // Consumir el salto de línea pendiente
                String nuevoTitulo = scanner.nextLine();

                System.out.println("Ingrese el nuevo autor del libro:");
                String nuevoAutor = scanner.nextLine();

                System.out.println("Ingrese el nuevo año de publicación del libro:");
                int nuevoAnioPublicacion = scanner.nextInt();

                scanner.nextLine(); // Limpiar el buffer

                // Solicitar al usuario que indique si el libro está disponible para préstamo
                System.out.println("¿El libro está disponible para préstamo? (Sí/No)");
                boolean disponibleParaPrestamo = scanner.nextLine().equalsIgnoreCase("Sí");

                // Actualizar los datos del libro
                libro.setTitulo(nuevoTitulo);
                libro.setAutor(nuevoAutor);
                libro.setAñoPublicacion(nuevoAnioPublicacion);
                libro.setDisponibleParaPrestamo(disponibleParaPrestamo);

                // Actualizar el libro en la base de datos
                session.update(libro);

                transaction.commit(); // Confirmar la transacción

                System.out.println("Libro actualizado correctamente.");
            } else {
                System.out.println("No se encontró ningún libro con el ID proporcionado.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

   
    private static void actualizarLector(Scanner scanner) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            System.out.println("Ingrese el ID del lector que desea actualizar:");
            int id = scanner.nextInt();

            Lector lector = session.get(Lector.class, id);
            if (lector != null) {
                System.out.println("Ingrese el nuevo nombre del lector:");
                scanner.nextLine(); // Limpiar el buffer
                String nuevoNombre = scanner.nextLine();

                System.out.println("Ingrese el nuevo apellido del lector:");
                String nuevoApellido = scanner.nextLine();

                System.out.println("Ingrese la nueva edad del lector:");
                int nuevaEdad = scanner.nextInt();

                scanner.nextLine(); // Limpiar el buffer

                // Actualizar los datos del lector
                lector.setNombre(nuevoNombre);
                lector.setApellido(nuevoApellido);
                lector.setEdad(nuevaEdad);

                // Actualizar el lector en la base de datos
                session.update(lector);

                transaction.commit();
                System.out.println("Lector actualizado correctamente.");
            } else {
                System.out.println("No se encontró ningún lector con el ID proporcionado.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
           e.printStackTrace();
        }
    }

    private static void borrarLibro(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            System.out.println("Ingrese el ID del libro que desea borrar:");
            int libroId = scanner.nextInt();

            // Consulta para contar los préstamos asociados al libro
            Long count = (Long) session.createQuery("select count(*) from Prestamo where libro.id = :libroId")
                    .setParameter("libroId", libroId)
                    .uniqueResult();

            if (count > 0) {
                System.out.println("No se puede borrar el libro porque tiene préstamos asociados.");
            } else {
                // Obtener el libro que se va a borrar
                Libro libro = session.get(Libro.class, libroId);

                if (libro != null) {
                    // Eliminar el libro si no tiene préstamos asociados
                    session.delete(libro);
                    transaction.commit();
                    System.out.println("Libro eliminado correctamente.");
                } else {
                    System.out.println("No se encontró ningún libro con el ID especificado.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void borrarLector(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            System.out.println("Ingrese el ID del lector que desea borrar:");
            int lectorId = scanner.nextInt();

            // Consulta para contar los préstamos asociados al lector
            Long count = (Long) session.createQuery("select count(*) from Prestamo where lector.id = :lectorId")
                    .setParameter("lectorId", lectorId)
                    .uniqueResult();

            if (count > 0) {
                System.out.println("No se puede borrar el lector porque tiene préstamos asociados.");
            } else {
                // Obtener el lector que se va a borrar
                Lector lector = session.get(Lector.class, lectorId);

                if (lector != null) {
                    // Eliminar el lector si no tiene préstamos asociados
                    session.delete(lector);
                    transaction.commit();
                    System.out.println("Lector eliminado correctamente.");
                } else {
                    System.out.println("No se encontró ningún lector con el ID especificado.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void asignarLibroALector(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Solicitar al usuario que ingrese el ID del libro
            System.out.println("Ingrese el ID del libro que desea asignar:");
            int idLibro = scanner.nextInt();
            Libro libro = session.get(Libro.class, idLibro);

            if (libro == null) {
                System.out.println("No se encontró ningún libro con el ID especificado.");
                return;
            }

            // Verificar si el libro ya está prestado
            if (!libro.isDisponibleParaPrestamo()) {
                System.out.println("El libro ya está prestado a otro lector.");
                return;
            }

            // Solicitar al usuario que ingrese el ID del lector
            System.out.println("Ingrese el ID del lector al que desea asignar el libro:");
            int idLector = scanner.nextInt();
            Lector lector = session.get(Lector.class, idLector);

            if (lector == null) {
                System.out.println("No se encontró ningún lector con el ID especificado.");
                return;
            }

            // Verificar si el lector ya tiene el máximo de libros prestados permitidos
            int maxLibrosPrestados = 5; // Por ejemplo, máximo 5 libros prestados por lector
            if (lector.getPrestamos().size() >= maxLibrosPrestados) {
                System.out.println("El lector ya tiene el máximo de libros prestados permitidos.");
                return;
            }

            // Establecer la relación entre el libro y el lector
            Prestamo prestamo = new Prestamo();
            prestamo.setLibro(libro);
            prestamo.setLector(lector);
            session.save(prestamo);

            // Actualizar el estado del libro como no disponible
            libro.setDisponibleParaPrestamo(false);
            session.update(libro);

            // Guardar la asignación en el historial
            AsignacionHistorial asignacionHistorial = new AsignacionHistorial();
            asignacionHistorial.setIdLector(idLector);
            asignacionHistorial.setIdLibro(idLibro);
            asignacionHistorial.setFechaAsignacion(LocalDateTime.now());
            session.save(asignacionHistorial);

            transaction.commit();
            System.out.println("Libro asignado al lector correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void registrarDevolucion(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Solicitar al usuario que ingrese el ID del libro que se va a devolver
            System.out.println("Ingrese el ID del libro que desea devolver:");
            int idLibro = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            // Obtener el libro que se va a devolver
            Libro libro = session.get(Libro.class, idLibro);

            if (libro == null) {
                System.out.println("No se encontró ningún libro con el ID especificado.");
                return;
            }

            // Verificar si el libro estaba prestado
            if (libro.isDisponible()) {
                System.out.println("El libro no estaba prestado.");
                return;
            }

            // Marcar el libro como disponible nuevamente
            libro.setDisponible(true);
            libro.setLector(null); // Eliminar la relación con el lector
            session.update(libro);

            transaction.commit();
            System.out.println("Devolución registrada correctamente. El libro ahora está disponible para préstamo.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listarLibros() {
        try (Session session = sessionFactory.openSession()) {
            List<Libro> libros = session.createQuery("FROM Libro", Libro.class).getResultList();
            if (!libros.isEmpty()) {
                System.out.println("Listado de libros:");
                for (Libro libro : libros) {
                    System.out.println(libro);
                }
            } else {
                System.out.println("No hay libros registrados en la base de datos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listarLectores() {
        try (Session session = sessionFactory.openSession()) {
            List<Lector> lectores = session.createQuery("FROM Lector", Lector.class).getResultList();
            if (!lectores.isEmpty()) {
                System.out.println("Listado de lectores:");
                for (Lector lector : lectores) {
                    System.out.println(lector);
                }
            } else {
                System.out.println("No hay lectores registrados en la base de datos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void verLibroPorId(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            System.out.println("Ingrese el ID del libro:");
            int id = scanner.nextInt();

            Libro libro = session.get(Libro.class, id);
            if (libro != null) {
                System.out.println(libro);
            } else {
                System.out.println("No se encontró ningún libro con el ID proporcionado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void verLectorPorId(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            System.out.println("Ingrese el ID del lector:");
            int id = scanner.nextInt();

            Lector lector = session.get(Lector.class, id);
            if (lector != null) {
                System.out.println(lector);
            } else {
                System.out.println("No se encontró ningún lector con el ID proporcionado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    
    
    
    private static void consultarLibrosPrestadosALector(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            System.out.println("Ingrese el ID del lector:");
            int idLector = scanner.nextInt();

            // Consultar los préstamos del lector con el ID especificado
            String jpql = "SELECT p FROM Prestamo p WHERE p.lector.id = :idLector";
            List<Prestamo> prestamos = session.createQuery(jpql, Prestamo.class)
                                              .setParameter("idLector", idLector)
                                              .getResultList();

            if (!prestamos.isEmpty()) {
                System.out.println("Listado de libros prestados al lector:");
                for (Prestamo prestamo : prestamos) {
                    Libro libroPrestado = prestamo.getLibro();
                    System.out.println("ID del libro: " + libroPrestado.getId() + ", Título: " + libroPrestado.getTitulo());
                }
            } else {
                System.out.println("El lector no tiene libros prestados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    


    
    
    
    
    private static void consultarHistorialPrestamosPorLector(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            // Solicitar al usuario que ingrese el ID del lector
            System.out.println("Ingrese el ID del lector para consultar su historial de préstamos:");
            int idLector = scanner.nextInt();

            // Consultar el historial de préstamos del lector con el ID especificado
            String jpql = "SELECT h FROM AsignacionHistorial h WHERE h.idLector = :idLector";
            List<AsignacionHistorial> historialPrestamos = session.createQuery(jpql, AsignacionHistorial.class)
                    .setParameter("idLector", idLector)
                    .getResultList();

            // Mostrar el historial de préstamos del lector
            if (!historialPrestamos.isEmpty()) {
                System.out.println("Historial de préstamos del lector " + idLector + ":");

                // Obtener el nombre del lector
                Lector lector = session.get(Lector.class, idLector);
                if (lector != null) {
                    System.out.println("Nombre del lector: " + lector.getNombre() + " " + lector.getApellido());
                }

                // Obtener detalles de los libros prestados
                for (AsignacionHistorial asignacion : historialPrestamos) {
                    Libro libro = session.get(Libro.class, asignacion.getIdLibro());
                    if (libro != null) {
                        System.out.println("ID del libro: " + asignacion.getIdLibro() + ", Título: " + libro.getTitulo());
                    }
                }
            } else {
                System.out.println("El lector no tiene historial de préstamos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private static void consultarLibrosDisponibles() {
        try (Session session = sessionFactory.openSession()) {
            List<Libro> librosDisponibles = session.createQuery("FROM Libro WHERE disponible = true", Libro.class).getResultList();
            if (!librosDisponibles.isEmpty()) {
                System.out.println("Listado de libros disponibles para préstamo:");
                for (Libro libro : librosDisponibles) {
                    System.out.println(libro);
                }
            } else {
                System.out.println("No hay libros disponibles para préstamo en este momento.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void consultarLibrosPrestados(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            System.out.println("Ingrese el ID del lector para consultar los libros prestados:");
            int idLector = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            session.beginTransaction();

            // Consultar los libros prestados por ID del lector
            String hql = "SELECT p.libro FROM Prestamo p WHERE p.lector.id = :idLector";
            Query<Libro> query = session.createQuery(hql, Libro.class);
            query.setParameter("idLector", idLector);
            List<Libro> librosPrestados = query.getResultList();

            // Mostrar los libros prestados
            if (librosPrestados.isEmpty()) {
                System.out.println("El lector no tiene libros prestados en este momento.");
            } else {
                System.out.println("Libros prestados al lector con ID " + idLector + ":");
                for (Libro libro : librosPrestados) {
                    System.out.println(libro);
                }
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            // Manejar cualquier excepción
            e.printStackTrace();
        }
    }

    }


    


  