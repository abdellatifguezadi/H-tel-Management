package Model;

import java.util.Objects;
import java.util.UUID;

public class Client {
    public enum Role {
        CLIENT , ADMIN
    }

    private UUID id;
    private String fullName;
    private String email;
    private String password;
    private Role role ;



    public Client(String fullName, String email, String password) {
        this.id = UUID.randomUUID();
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = Role.CLIENT;
    }

    public Client(String fullName, String email, String password, Role role) {
        this.id = UUID.randomUUID();
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Constructeur pour la reconstruction depuis le repository
    public Client(UUID id, String fullName, String email, String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = Role.CLIENT;
    }

    // Constructeur complet pour la reconstruction depuis le repository
    public Client(UUID id, String fullName, String email, String password, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public boolean isClient() {
        return role == Role.CLIENT;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email, password);
    }

    @Override
    public String toString() {
        return "Client{" +
                "fullName='" + fullName + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}


