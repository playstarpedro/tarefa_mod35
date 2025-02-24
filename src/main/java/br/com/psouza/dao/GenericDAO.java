package main.java.br.com.psouza.dao;

import main.java.br.com.psouza.domain.Persistent;

import java.util.List;
import java.util.Collection;
import java.io.Serializable;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public abstract class GenericDAO <T extends Persistent, ID extends Serializable> implements IGenericDAO<T, ID>{
    private static final String PERSISTENCE_UNIT_NAME = "Postgre1";

    protected EntityManagerFactory entityManagerFactory;

    protected EntityManager entityManager;

    private String persistenceUnitName;

    private Class<T> persistenteClass;


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
        T entity = entityManager.find(this.persistenteClass, id);
        entityManager.getTransaction().commit();
        closeConnection();
        return entity;
    }

    @Override
    public Collection searchAll() {
        openConnection();
        List<T> list =
                entityManager.createQuery(getSelectSql(), this.persistenteClass).getResultList();
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
            return PERSISTENCE_UNIT_NAME;
        }
    }

    private String getSelectSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT obj FROM ");
        sb.append(this.persistenteClass.getSimpleName());
        sb.append(" obj");
        return sb.toString();
    }
}
