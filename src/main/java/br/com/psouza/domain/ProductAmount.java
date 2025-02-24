package main.java.br.com.psouza.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "TB_PRODUCT_AMOUNT")
public class ProductAmount implements Persistent {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="prod_amount_seq")
    @SequenceGenerator(name="prod_amount_seq", sequenceName="sq_prod_amount", initialValue = 1, allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @Column(name = "AMOUNT", nullable = false)
    private Integer amount;

    @Column(name = "TOTAL_VALUE", nullable = false)
    private BigDecimal totalValue;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sale_fk",
            foreignKey = @ForeignKey(name = "fk_prod_amount_sale"),
            referencedColumnName = "id", nullable = false
    )
    private Sale sale;

    public ProductAmount(Integer amount, BigDecimal totalValue) {
        this.amount = amount;
        this.totalValue = totalValue;
    }

    public ProductAmount(Product produto, Integer amount, BigDecimal totalValue, Sale sale) {
        this.product = produto;
        this.amount = amount;
        this.totalValue = totalValue;
        this.sale = sale;
    }

    public void add(Integer amount) {
        this.amount += amount;
        BigDecimal newValue = this.product.getValue().multiply(BigDecimal.valueOf((amount))) ;
        this.totalValue = this.totalValue.add(newValue);
    }

    public void remove(Integer amount) {
        this.amount -= amount;
        BigDecimal newValue = this.product.getValue().multiply(BigDecimal.valueOf(amount));
        this.totalValue = this.totalValue.subtract((newValue));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAmount that = (ProductAmount) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
