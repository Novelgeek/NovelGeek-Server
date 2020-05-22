package lk.ucsc.NovelGeek.model;

import javax.persistence.*;

@Entity(name="auths")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String role;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    private String encryptedPassword;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
