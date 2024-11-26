package org.example.DAO.entities;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Curso implements Serializable {
    private Integer id;
    private String codigo;
    private String titulo;

    //Se podría implementar el código necesario para que al obtener un curso se obtengan también sus alumnos y se rellene este ArrayList, pero no lo haremos porque en las príximas lecciones lo hará el ORM automáticamente
    //List<Alumno> alumnos = new ArrayList<>();

}
