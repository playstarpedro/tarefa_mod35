package test.br.com.psouza;

import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import main.java.br.com.psouza.dao.ProductDAO;
import main.java.br.com.psouza.domain.Product;
import main.java.br.com.psouza.dao.IGenericDAO;
import main.java.br.com.psouza.domain.Persistent;

import java.util.List;
import java.math.BigDecimal;
import java.util.Collection;

public class ProductTest {
    private IGenericDAO productDAO;

    public ProductTest() {
        this.productDAO = new ProductDAO(Product.class, "postgres");
    }

    @After
    public void end() {
        Collection<Product> products = productDAO.searchAll();
        products.forEach(product -> {
            try {
                productDAO.delete(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void shouldRegisterAProduct() {
        Product registeredProduct = (Product) productDAO.register(createProduct("123123123123"));

        Product consultedProduct = (Product) productDAO.consult(registeredProduct.getId());
        Assert.assertNotNull(consultedProduct);
        Assert.assertEquals(consultedProduct.getCode(), registeredProduct.getCode());
    }

    @Test
    public void shouldUpdateAProduct() {
        Product registeredProduct = (Product) productDAO.register(createProduct("123"));

        registeredProduct.setCode("170");
        productDAO.update(registeredProduct);
        Product consultedProduct = (Product) productDAO.consult(registeredProduct.getId());

        Assert.assertEquals(consultedProduct.getCode(), "170");
        Assert.assertNotEquals(consultedProduct.getCode(), "123");
    }

    @Test
    public void shouldSearchAllProducts() {
        Product registeredProduct1 = (Product) productDAO.register(createProduct("1234567890"));
        Product registeredProduct2 = (Product) productDAO.register(createProduct("0987654321"));

        List<Product> clients = (List<Product>) productDAO.searchAll();
        Assert.assertNotNull(clients);
        Assert.assertEquals(clients.get(0).getCode(), registeredProduct1.getCode());
        Assert.assertEquals(clients.get(1).getCode(), registeredProduct2.getCode());
    }

    @Test
    public void shouldConsultAProduct() {
        Product product = (Product) productDAO.register(createProduct("123123123123"));

        Product consultedProduct = (Product) productDAO.consult(product.getId());
        Assert.assertNotNull(consultedProduct);
        Assert.assertEquals(consultedProduct.getCode(), product.getCode());
    }

    @Test
    public void shouldDeleteAProduct() {
        Product product = (Product) productDAO.register(createProduct("123123123123"));

        Product registeredProduct = (Product) productDAO.consult(product.getId());
        productDAO.delete(registeredProduct);

        Persistent consultedProduct = productDAO.consult(registeredProduct.getId());
        Assert.assertNull(consultedProduct);
    }

    private Product createProduct(String code) {
        return new Product(
                code,
                "Product Test",
                "Product description test",
                BigDecimal.valueOf(15.00)
        );
    }
}
