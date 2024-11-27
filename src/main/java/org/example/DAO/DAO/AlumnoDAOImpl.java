package org.example.DAO.DAO;

import org.example.DAO.entities.Alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAOImpl implements AlumnoDAO<Alumno> {
    private final Connection connection;

    public AlumnoDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Alumno alumno) {
        String sql = "INSERT INTO alumno (nombre, apellidos, edad, fecha_registro, id_curso) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, alumno.getNombre());
            stmt.setString(2, alumno.getApellidos());
            stmt.setInt(3, alumno.getEdad());
            stmt.setTimestamp(4, Timestamp.valueOf(alumno.getFechaRegistro()));
            stmt.setObject(5, alumno.getIdCurso(), Types.INTEGER);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    alumno.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Alumno findById(Integer id) {
        String sql = "SELECT * FROM alumno WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Alumno(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellidos"),
                            rs.getInt("edad"),
                            rs.getTimestamp("fecha_registro").toLocalDateTime(),
                            rs.getObject("id_curso", Integer.class)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Alumno> findAll() {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM alumno";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                alumnos.add(new Alumno(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getInt("edad"),
                        rs.getTimestamp("fecha_registro").toLocalDateTime(),
                        rs.getObject("id_curso", Integer.class)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }

    @Override
    public void update(Alumno alumno) {
        String sql = "UPDATE alumno SET nombre = ?, apellidos = ?, edad = ?, id_curso = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, alumno.getNombre());
            stmt.setString(2, alumno.getApellidos());
            stmt.setInt(3, alumno.getEdad());
            stmt.setObject(4, alumno.getIdCurso(), Types.INTEGER);
            stmt.setInt(5, alumno.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM alumno WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
