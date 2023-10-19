package ntnu.idata2503.group9.stockappbackend.Models;

import jakarta.persistence.*;

@Table
public class User {

    @Id
    @GeneratedValue
    long Uid;
    String email;
    String password;

    public User(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    public long getUid() {
        return this.Uid;
    }

    public String getName() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
