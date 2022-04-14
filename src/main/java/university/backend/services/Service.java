package university.backend.services;

import java.util.List;

public interface Service<T> {
    public void persist(T entity);

    public void update(T entity);

    public T findById(Long id);

    public void delete(Long id);

    List<T> findAll();

    void deleteAll();
}