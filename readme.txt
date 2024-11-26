# Tablas para la BD instituto

CREATE TABLE curso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(255) NOT NULL,
    titulo VARCHAR(255) NOT NULL
);

CREATE TABLE alumno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    edad INT NOT NULL,
    fecha_registro DATETIME NOT NULL,
    id_curso INT,
    FOREIGN KEY (id_curso) REFERENCES curso(id) ON DELETE CASCADE ON UPDATE CASCADE
);
