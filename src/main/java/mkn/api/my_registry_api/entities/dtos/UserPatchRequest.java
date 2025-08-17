package mkn.api.my_registry_api.entities.dtos;

import jakarta.validation.constraints.Email;
import mkn.api.my_registry_api.entities.enums.UserRole;

public class UserPatchRequest {
    private String name;
    private String cpf;
    private Integer role;
    private String password;

    @Email(message = "Invalid email")
    private String email;


    //Getters and Setters
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
