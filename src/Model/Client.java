package Model;

import java.util.Objects;
import java.util.UUID;

public class Client {
    private  UUID id ;
    private  String fullName ;
    private  String email ;
    private  String password;

    public Client(UUID id , String fullName , String email , String password){
        this.id = id;
        this.fullName = fullName;
        this.email = email ;
        this.password = password ;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
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


