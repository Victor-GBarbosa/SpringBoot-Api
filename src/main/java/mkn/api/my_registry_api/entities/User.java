package mkn.api.my_registry_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import mkn.api.my_registry_api.entities.enums.OrderStatus;
import mkn.api.my_registry_api.entities.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_user")
public class User implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    // Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 528)
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String cpf;

    private Integer role;


    @JoinColumn(name = "user_id")
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Order> order = new ArrayList();

    //Methods

    public void addOrder(Order order) {

        boolean hasUnfinishedOrder = this.order.stream().anyMatch(orderItem -> orderItem.getOrderStatus() == OrderStatus.CART);

        if(hasUnfinishedOrder) {

        } else {
            this.order.add(order);
        }
    }

    public boolean addOrderProduct(OrderProduct orderProduct) {
        return this.order.stream()
                .filter(o -> o.getOrderStatus() == OrderStatus.CART)
                .findFirst()
                .map(o -> {
                    o.addOrderProduct(orderProduct);
                    return true;
                })
                .orElse(false);
    }

    public boolean closeOrder() {
        List<Order> orderToClose = this.order.stream()
                .filter(o -> o.getOrderStatus() == OrderStatus.CART).collect(Collectors.toList());
        if(orderToClose.size() != 1) {
            return false;
        } else {
            orderToClose.get(0).setOrderStatus(OrderStatus.PENDING_PAYMENT);
            return true;
        }
    }

    // Constructors

    public User() {
    }

    public User(String name,  String email, String password, String phoneNumber, String cpf) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
        this.role = 1;
        this.order.add(new Order());

    }

    // Getters and Setters

    @JsonIgnore
    public List<Order> getOrder() {
        return order;
    }

    public Integer getRole() {
        return role;
    }

    public void setRoleStatus(UserRole role) {
        this.role = role.getId();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //HasgCode and Equals


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }

    //JWT UserDetails methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == null) {
            return List.of();
        }

        try {
            UserRole userRole = UserRole.valueOf(this.role);
            return getAuthoritiesForRole(userRole);
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

    private Collection<? extends GrantedAuthority> getAuthoritiesForRole(UserRole userRole) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // Adiciona o role atual
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.name()));

        // Adiciona roles de menor hierarquia baseado no ID
        // Um role com ID maior inclui automaticamente todos os roles com ID menor
        for (UserRole role : UserRole.values()) {
            if (role.getId() < userRole.getId()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
            }
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return String.valueOf(id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", order=" + order.toString() +
                '}';
    }
}
