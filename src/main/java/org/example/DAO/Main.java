package org.example.DAO;


import org.example.DAO.DAO.AlumnoDAO;
import org.example.DAO.DAO.AlumnoDAOImpl;
import org.example.DAO.DAO.CursoDAO;
import org.example.DAO.DAO.CursoDAOImpl;
import org.example.DAO.entities.Alumno;
import org.example.DAO.entities.Curso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/instituto";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            CursoDAOImpl cursoDAO = new CursoDAOImpl(connection);
            AlumnoDAOImpl alumnoDAO = new AlumnoDAOImpl(connection);
            Scanner scanner = new Scanner(System.in);

            // Crear un curso
            Curso curso = new Curso(null, "CURSO123", "Curso de Prueba");
            cursoDAO.create(curso);

            // Crear alumnos
            Alumno alumno1 = new Alumno(null, "Juan", "Pérez", 20, LocalDateTime.now(), curso.getId());
            Alumno alumno2 = new Alumno(null, "Pilar", "Martinez", 22, LocalDateTime.now(), curso.getId());
            alumnoDAO.create(alumno1);
            alumnoDAO.create(alumno2);

            // Imprimir todos los alumnos
            System.out.println("Listado de alumnos en la BD:");
            alumnoDAO.findAll().forEach(System.out::println);

            // Borrar un alumno
            System.out.print("\nIntroduce el ID de un alumno para borrar: ");
            int idBorrar = scanner.nextInt();
            Alumno alumnoABorrar = alumnoDAO.findById(idBorrar);

            if (alumnoABorrar != null) {
                alumnoDAO.delete(idBorrar);
                System.out.println("Alumno borrado: " + alumnoABorrar);
            } else {
                System.out.println("No se encontró un alumno con el ID proporcionado.");
            }

            System.out.println("Introduce el ID de un alumno a actualizar: ");
            int idActualizar = scanner.nextInt();
            Alumno alumnoAActualizar = alumnoDAO.findById(idActualizar);
            if (alumnoAActualizar != null) {
                System.out.println("Nombre actual: " + alumnoAActualizar.getNombre() + ". Introduce el nuevo nombre: ");
                String nuevoNombre = scanner.next();
                alumnoAActualizar.setNombre(nuevoNombre);
                alumnoDAO.update(alumnoAActualizar);
                System.out.println("Alumno actualizado: " + alumnoAActualizar);
            } else {
                System.out.println("No se encontró un alumno con el ID proporcionado.");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
