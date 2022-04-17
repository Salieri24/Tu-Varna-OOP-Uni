package university.backend.services;

import java.util.List;

public interface Service<T> {
    T persist(T entity);

    void update(T entity);

    T findById(Long id);

    void delete(Long id);

    List<T> findAll();

    void deleteAll();
}