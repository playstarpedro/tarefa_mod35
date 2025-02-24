package main.java.br.com.psouza.dao;

import main.java.br.com.psouza.domain.Persistent;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericDAO <T extends Persistent, E extends Serializable> {
    public T register(T entity);

    public void delete(T entity);

    public T update(T entity);

    public T consult(E id);

    public Collection<T> searchAll();
}
