package main.java.br.com.psouza.dao;

import main.java.br.com.psouza.domain.Sale;

public class SaleDAO extends GenericDAO<Sale, Long>{
    public SaleDAO(Class<Sale> persistentClass) {
        super(Sale.class);
    }
}
