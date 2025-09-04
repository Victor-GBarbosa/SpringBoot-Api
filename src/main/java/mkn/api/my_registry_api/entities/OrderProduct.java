package mkn.api.my_registry_api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_order_procduct")
public class OrderProduct implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

//    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private Integer quantity;
    private Double subtotal;

    // Methods
    @Transactional
    public OrderProduct addToOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        if (this.order != null && this.order != order) {
            throw new IllegalStateException("This OrderProduct is already attached to another Order");
        }

        if(!order.getOrderProductList().contains(this)) {
            this.order = order;

        }
        return this;
    }

    public void reCalcSubtotal() {
        subtotal = quantity * product.getPrice();
    }

    //Constructors

    public OrderProduct(Product product, Integer quantity, Order order) {
        addToOrder(order);
        this.product = product;
        this.quantity = quantity;
        this.subtotal = this.getSubtotal();
    }
    public OrderProduct() {
    }


    //Getters and Setters


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubtotal() {
        return this.product.getPrice() * this.quantity;
    }

    
    //HashCode and Equals


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderProduct that = (OrderProduct) o;
        return Objects.equals(id, that.id) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product);
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "product=" + product +
                '}';
    }
}
