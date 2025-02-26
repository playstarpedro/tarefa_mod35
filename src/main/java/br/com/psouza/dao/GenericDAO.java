package main.java.br.com.psouza.dao;

import main.java.br.com.psouza.domain.Persistent;

import java.util.List;
import java.util.Collection;
import java.io.Serializable;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public abstract class GenericDAO<T extends Persistent, ID extends Serializable> implements IGenericDAO<T, ID> {

    protected EntityManagerFactory entityManagerFactory;

    protected EntityManager entityManager;

    private String persistenceUnitName = "postgres";

    private final Class<T> persistentClass;

    public GenericDAO(Class<T> persistentClass, String persistenceUnitName) {
        this.persistentClass = persistentClass;
        this.persistenceUnitName = persistenceUnitName;
    }

    @Override
    public T register(T entity) {
        openConnection();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        closeConnection();
        return entity;
    }

    @Override
    public void delete(T entity) {
        openConnection();
        entity = entityManager.merge(entity);
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
        closeConnection();
    }

    @Override
    public T update(T entity) {
        openConnection();
        entity = entityManager.merge(entity);
        entityManager.getTransaction().commit();
        closeConnection();
        return entity;
    }

    @Override
    public T consult(ID id) {
        openConnection();
        T entity = entityManager.find(this.persistentClass, id);
        entityManager.getTransaction().commit();
        closeConnection();
        return entity;
    }

    @Override
    public Collection searchAll() {
        openConnection();
        List<T> list =
                entityManager.createQuery(getSelectSql(), this.persistentClass).getResultList();
        closeConnection();
        return list;
    }

    protected void openConnection() {
        entityManagerFactory =
                Persistence.createEntityManagerFactory(getPersistenceUnitName());
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    protected void closeConnection() {
        entityManager.close();
        entityManagerFactory.close();
    }

    private String getPersistenceUnitName() {
        if (persistenceUnitName != null
                && !"".equals(persistenceUnitName)) {
            return persistenceUnitName;
        } else {
            return persistenceUnitName;
        }
    }

    private String getSelectSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT obj FROM ");
        sb.append(this.persistentClass.getSimpleName());
        sb.append(" obj");
        return sb.toString();
    }
}
