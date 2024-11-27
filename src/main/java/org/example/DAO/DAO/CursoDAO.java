package org.example.DAO.DAO;

import java.util.List;

public interface CursoDAO<T> {
    void create(T entity);
    T findById(Integer id);
    List<T> findAll();
    void update(T entity);
    void delete(Integer id);
}