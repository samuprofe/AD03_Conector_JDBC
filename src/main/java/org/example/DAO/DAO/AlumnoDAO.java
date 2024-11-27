package org.example.DAO.DAO;

import java.util.List;

public interface AlumnoDAO<T> {
    void create(T entity);
    T findById(Integer id);
    List<T> findAll();
    void update(T entity);
    void delete(Integer id);
}