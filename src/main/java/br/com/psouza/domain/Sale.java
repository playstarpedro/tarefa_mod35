package main.java.br.com.psouza.domain;

import main.java.br.com.psouza.domain.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TB_SALE")
public class Sale implements Persistent {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sale_seq")
    @SequenceGenerator(name = "sale_seq", sequenceName = "sq_sale", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "CODE", nullable = false, unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "id_client_fk",
        foreignKey = @ForeignKey(name = "fk_sale_client"),
            referencedColumnName = "id", nullable = false
    )
    private Client client;

    @OneToMany(mappedBy = "SALE", cascade = CascadeType.ALL)
    private Set<ProductAmount> products;

    @Column(name = "TOTAL_VALUE", nullable = false)
    private BigDecimal totalValue;

    @Column(name = "SALE_DATE", nullable = false)
    private Instant saleDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "SALE_STATUS", nullable = false)
    private Status status;

    public Sale(Status status) {
        this.status = status;
    }

    public Sale(String code, Client client, Set<ProductAmount> products, BigDecimal totalValue, Instant saleDate, Status status) {
        this.code = code;
        this.client = client;
        this.products = products;
        this.totalValue = totalValue;
        this.saleDate = saleDate;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(code, sale.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<ProductAmount> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductAmount> products) {
        this.products = products;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public Instant getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Instant saleDate) {
        this.saleDate = saleDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
