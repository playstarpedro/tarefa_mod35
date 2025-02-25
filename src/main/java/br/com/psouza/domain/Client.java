package main.java.br.com.psouza.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TB_CLIENT")
public class Client implements Persistent {

    @Id
    @SequenceGenerator(name = "client_seq", sequenceName = "sq_client", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "CPF", nullable = false, unique = true)
    private String cpf;

    @Column(name = "TEL", nullable = false)
    private String tel;

    @Column(name = "ADDRESS", nullable = false, length = 100)
    private String address;

    @Column(name = "ADDRESS_NUMBER", nullable = false)
    private Integer addressNumber;

    @Column(name = "CITY", nullable = false, length = 100)
    private String city;

    @Column(name = "STATE", nullable = false, length = 50)
    private String state;

    public Client() {
    }

    public Client(String name, String cpf, String tel, String address, Integer addressNumber, String city, String state) {
        this.name = name;
        this.cpf = cpf;
        this.tel = tel;
        this.address = address;
        this.addressNumber = addressNumber;
        this.city = city;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(Integer addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(cpf, client.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }
}
