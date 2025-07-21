package mkn.api.my_registry_api.entities;

import jakarta.persistence.*;
import mkn.api.my_registry_api.entities.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    }

    // Getters and Setters


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

        if (this.role == 3) {
            return List.of(new SimpleGrantedAuthority("ROLE_" +"3"), new SimpleGrantedAuthority("ROLE_" +"2"), new SimpleGrantedAuthority("ROLE_" + "1"));
        } else if (this.role == 2) {
            return List.of(new SimpleGrantedAuthority("ROLE_" +"2"),new SimpleGrantedAuthority("ROLE_" +"1"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_" +"1"));
        }
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
}
