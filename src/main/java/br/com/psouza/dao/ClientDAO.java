package main.java.br.com.psouza.dao;

import main.java.br.com.psouza.domain.Client;

public class ClientDAO extends GenericDAO<Client, Long>{
    public ClientDAO(Class<Client> persistentClass, String persistenceUnitName) {
        super(persistentClass, persistenceUnitName);
    }
}
