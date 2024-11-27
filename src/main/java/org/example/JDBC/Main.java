package org.example.JDBC;

import java.sql.*;
import java.time.LocalDateTime;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/instituto";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // Insertar un curso recuperando la id que se le ha asignado la BD al campo autonumérico. Este id lo necesitamos para insertar después los alumnos.
            String insertarCursoSQL = "INSERT INTO curso (codigo, titulo) VALUES (?, ?)";
            int idCurso;
            try (PreparedStatement stmt = connection.prepareStatement(insertarCursoSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, "CURSO123");
                stmt.setString(2, "Curso de Prueba");
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    idCurso = rs.next() ? rs.getInt(1) : -1;
                }
            }
            System.out.println("1 curso insertado");

            // Insertar alumnos sin recuperar la id que se les asigna en el campo autonumérico
            String insertarAlumnoSQL = "INSERT INTO alumno (nombre, apellidos, edad, fecha_registro, id_curso) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(insertarAlumnoSQL)) {
                stmt.setString(1, "Juan");
                stmt.setString(2, "Pérez");
                stmt.setInt(3, 20);
                stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                stmt.setInt(5, idCurso);
                stmt.executeUpdate();
            }
            insertarAlumnoSQL = "INSERT INTO alumno (nombre, apellidos, edad, fecha_registro, id_curso) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(insertarAlumnoSQL)) {
                stmt.setString(1, "Pilar");
                stmt.setString(2, "Martinez");
                stmt.setInt(3, 22);
                stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                stmt.setInt(5, idCurso);
                stmt.executeUpdate();
            }

            System.out.println("2 Alumnos insertados");


            // Actualizar un alumno
            int numeroRegistrosActualizados;
            String actualizarAlumnoSQL = "UPDATE alumno SET nombre = ?, apellidos = ?, edad = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(actualizarAlumnoSQL)) {
                stmt.setString(1, "Juan Carlos");
                stmt.setString(2, "Pérez García");
                stmt.setInt(3, 21);
                stmt.setInt(4, 1);  //Actualizamos el alumno con id = 1
                numeroRegistrosActualizados = stmt.executeUpdate();
            }
            System.out.println(numeroRegistrosActualizados + " alumno actualizado.");

            // Obtener e imprimir un alumno
            String obtenerAlumnoPorIdSQL = "SELECT * FROM alumno WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(obtenerAlumnoPorIdSQL)) {
                stmt.setInt(1, 1);  //Obtenemos los datos del alumno 1
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {    //Si se ha obtenido al menos una fila
                        System.out.printf("Alumno obtenido de la BD: %n  - ID: %d, Nombre: %s, Apellidos: %s, Edad: %d, Fecha Registro: %s%n",
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("apellidos"),
                                rs.getInt("edad"),
                                rs.getTimestamp("fecha_registro").toLocalDateTime());
                    }
                }
            }

            // Obtener e imprimir todos los alumnos
            String obtenerAlumnosSQL = "SELECT * FROM alumno";
            try (PreparedStatement stmt = connection.prepareStatement(obtenerAlumnosSQL)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    System.out.println("Alumnos obtenidos de la BD:");
                    while (rs.next()) { //Mientras haya más registros en el ResultSet...
                        System.out.printf("  - ID: %d, Nombre: %s, Apellidos: %s, Edad: %d, Fecha Registro: %s %n",
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("apellidos"),
                                rs.getInt("edad"),
                                rs.getTimestamp("fecha_registro").toLocalDateTime());
                    }
                }
            }

            // Eliminar un alumno
            String eliminarAlumnoSQL = "DELETE FROM alumno WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(eliminarAlumnoSQL)) {
                stmt.setInt(1, 2);  //Borramos el alumno 2 si existe
                if(stmt.executeUpdate()==1){
                    System.out.println("Alumno con id 2 eliminado.");
                }
                else{
                    System.out.println("Alumno con id 2 no encontrado. No se ha podido eliminarlo.");
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
