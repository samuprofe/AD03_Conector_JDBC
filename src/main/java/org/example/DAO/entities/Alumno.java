package org.example.DAO.entities;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alumno implements Serializable {
    private Integer id;
    private String nombre;
    private String apellidos;
    private Integer edad;
    private LocalDateTime fechaRegistro;
    private Integer IdCurso;    //Se podría sustituir por un "private Curso curso", pero no lo haremos por no compliccar el código y porque el ORM lo hará por nosotros en las siguientes unidades
}
