package by.bsac.telephonestation.dao;

public interface GenericDAO<K, T> {

        T findEntityById(K id);

        boolean remove(K id);

        boolean create(T entity);

        boolean update(T entity);
}