package Model;

import java.util.Objects;
import java.util.UUID;

public class Client {
    public enum Role {
        CLIENT, ADMIN
    }

    private final UUID id;
    private String fullName;
    private String email;
    private String password;
    private final Role role;

    public Client(String fullName, String email, String password, Role role) {
        this.id = UUID.randomUUID();
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

    public boolean isAdmin() {
        return role == Role.ADMIN;
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
