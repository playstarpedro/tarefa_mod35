package test.br.com.psouza;

import main.java.br.com.psouza.dao.ClientDAO;
import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import main.java.br.com.psouza.domain.Sale;
import main.java.br.com.psouza.dao.SaleDAO;
import main.java.br.com.psouza.domain.Client;
import main.java.br.com.psouza.dao.IGenericDAO;
import main.java.br.com.psouza.domain.Persistent;
import main.java.br.com.psouza.domain.enums.Status;
import main.java.br.com.psouza.domain.ProductAmount;

import java.util.Set;
import java.util.List;
import java.time.Instant;
import java.util.HashSet;
import java.util.Collection;
import java.math.BigDecimal;

public class SaleTest {
    private IGenericDAO saleDAO;
    private IGenericDAO clientDAO;

    public SaleTest() {
        this.saleDAO = new SaleDAO(Sale.class, "postgres");
        this.clientDAO = new ClientDAO(Client.class, "postgres");
    }

    @After
    public void end() {
        Collection<Sale> sales = saleDAO.searchAll();
        sales.forEach(sale -> {
            try {
                saleDAO.delete(sale);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Collection<Client> clients = clientDAO.searchAll();
        clients.forEach(client -> {
            try {
                clientDAO.delete(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void shouldRegisterASale() {
        Sale registeredSale = (Sale) saleDAO.register(createSale("123123123123"));

        Sale consultedSale = (Sale) saleDAO.consult(registeredSale.getId());
        Assert.assertNotNull(consultedSale);
        Assert.assertEquals(consultedSale.getCode(), registeredSale.getCode());
    }

    @Test
    public void shouldUpdateASale() {
        Sale registeredSale = (Sale) saleDAO.register(createSale("123123123123"));

        registeredSale.setCode("99999999999");
        saleDAO.update(registeredSale);
        Sale consultedSale = (Sale) saleDAO.consult(registeredSale.getId());

        Assert.assertEquals(consultedSale.getCode(), "99999999999");
        Assert.assertNotEquals(consultedSale.getCode(), "123123123123");
    }

    @Test
    public void shouldSearchAllSales() {
        Sale registeredSale1 = (Sale) saleDAO.register(createSale("1234567890"));
        Sale registeredSale2 = (Sale) saleDAO.register(createSale("0987654321"));

        List<Sale> sales = (List<Sale>) saleDAO.searchAll();
        Assert.assertNotNull(sales);
        Assert.assertEquals(sales.get(0).getCode(), registeredSale1.getCode());
        Assert.assertEquals(sales.get(1).getCode(), registeredSale2.getCode());
    }

    @Test
    public void shouldConsultASale() {
        Sale sale = (Sale) saleDAO.register(createSale("123123123123"));

        Sale consultedSale = (Sale) saleDAO.consult(sale.getId());
        Assert.assertNotNull(consultedSale);
        Assert.assertEquals(consultedSale.getCode(), sale.getCode());
    }

    @Test
    public void shouldDeleteASale() {
        Sale sale = (Sale) saleDAO.register(createSale("123123123123"));

        Sale registeredSale = (Sale) saleDAO.consult(sale.getId());
        saleDAO.delete(registeredSale);

        Persistent consultedSale = saleDAO.consult(registeredSale.getId());
        Assert.assertNull(consultedSale);
    }

    private Sale createSale(String code) {
        Set<ProductAmount> prodcuts = new HashSet<>();
        Client client = (Client) clientDAO.register(createClient(code));

        return new Sale(
                code,
                client,
                prodcuts,
                BigDecimal.valueOf(100.20),
                Instant.now(),
                Status.INITIATED
        );
    }

    private Client createClient(String cpf) {
        return new Client(
                "Peter",
                cpf,
                "11966666666",
                "Test Street",
                501,
                "São Paulo",
                "SP"
        );
    }
}
