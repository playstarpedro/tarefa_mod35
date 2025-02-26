package test.br.com.psouza;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import main.java.br.com.psouza.dao.ClientDAO;
import main.java.br.com.psouza.dao.IGenericDAO;
import main.java.br.com.psouza.domain.Client;
import main.java.br.com.psouza.domain.Persistent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class ClientTest {

    private IGenericDAO clientDAO;
    private String persistenceUnitName;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"postgres"},
                {"Postgre2"},
                {"Mysql1"}
        });
    }

    public ClientTest(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
        this.clientDAO = new ClientDAO(Client.class, persistenceUnitName);
    }

    @After
    public void end() {
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
    public void shouldRegisterAClient() {
        Client registeredClient = (Client) clientDAO.register(createClient("123123123123"));
        Client consultedClient = (Client) clientDAO.consult(registeredClient.getId());

        Assert.assertNotNull(consultedClient);
        Assert.assertEquals(consultedClient.getCpf(), registeredClient.getCpf());
    }

    @Test
    public void shouldUpdateAClient() {
        Client registeredClient = (Client) clientDAO.register(createClient("123123123123"));

        registeredClient.setCpf("99999999999");
        clientDAO.update(registeredClient);
        Client consultedClient = (Client) clientDAO.consult(registeredClient.getId());

        Assert.assertEquals(consultedClient.getCpf(), "99999999999");
        Assert.assertNotEquals(consultedClient.getCpf(), "123123123123");
    }

    @Test
    public void shouldSearchAllClients() {
        Client registeredClient1 = (Client) clientDAO.register(createClient("1234567890"));
        Client registeredClient2 = (Client) clientDAO.register(createClient("0987654321"));

        List<Client> clients = (List<Client>) clientDAO.searchAll();
        Assert.assertNotNull(clients);
        Assert.assertEquals(clients.get(0).getCpf(), registeredClient1.getCpf());
        Assert.assertEquals(clients.get(1).getCpf(), registeredClient2.getCpf());
    }

    @Test
    public void shouldConsultAClient() {
        Client client = (Client) clientDAO.register(createClient("123123123123"));

        Client consultedClient = (Client) clientDAO.consult(client.getId());
        Assert.assertNotNull(consultedClient);
        Assert.assertEquals(consultedClient.getCpf(), client.getCpf());
    }

    @Test
    public void shouldDeleteAClient() {
        Client client = (Client) clientDAO.register(createClient("123123123123"));

        Client registeredClient = (Client) clientDAO.consult(client.getId());
        clientDAO.delete(registeredClient);

        Persistent consultedClient = clientDAO.consult(registeredClient.getId());
        Assert.assertNull(consultedClient);
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
