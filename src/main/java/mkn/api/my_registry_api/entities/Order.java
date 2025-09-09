package mkn.api.my_registry_api.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import mkn.api.my_registry_api.entities.enums.OrderStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_order")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "order",
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<OrderProduct> orderProductList = new ArrayList<>();

    @Column(nullable = false)
    private OrderStatus orderStatus;

    private double total;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant moment;

    //Methods


    public double recalcTotal() {
        for(OrderProduct orderProduct: this.orderProductList) {
            this.total += orderProduct.getSubtotal();
        }

        return total;
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        if(this.orderStatus == OrderStatus.CART) {
            this.orderProductList.add(orderProduct);
        }
    }

    //Constructors

    public Order() {
        this.orderStatus = OrderStatus.CART;
    }


    // Getters And Setters

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public double getTotal() {
        return this.recalcTotal();
    }

    public Long getId() {
        return id;
    }

    public List<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    //HasCode and Equals


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(total, order.total) == 0 && Objects.equals(id, order.id) && Objects.equals(orderProductList, order.orderProductList) && orderStatus == order.orderStatus && Objects.equals(moment, order.moment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderProductList, orderStatus, total, moment);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderProductList=" + orderProductList.toString() +
                '}';
    }
}
