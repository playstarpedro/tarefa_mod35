package main.java.br.com.psouza.dao;

import main.java.br.com.psouza.domain.Product;

public class ProductDAO extends GenericDAO<Product, Long>{
    public ProductDAO(Class<Product> persistentClass) {
        super(Product.class);
    }
}
