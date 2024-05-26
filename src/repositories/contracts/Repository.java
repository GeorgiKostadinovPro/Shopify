package repositories.contracts;

import java.util.Collection;

public interface Repository<T> {
    void add(T entity);

    void remove(int id);

    T getById(int id);

    Collection<T> getAll();
}
