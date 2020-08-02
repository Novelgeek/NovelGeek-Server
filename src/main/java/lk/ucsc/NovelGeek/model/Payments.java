package lk.ucsc.NovelGeek.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity(name = "PaymentSellingBooks")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long payid;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "sellingid")
    private SellBook sellbook;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    Users users;

    private String payment_id;
    private int status_code;
    private String status_message;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;

    public long getPayid() { return payid; }
    public void setPayid(long payid) { this.payid = payid; }

    public SellBook getSellbook() { return sellbook; }
    public void setSellbook(SellBook sellbook) { this.sellbook = sellbook; }

    public Users getUsers() { return users; }
    public void setUsers(Users users) { this.users = users; }

    public String getPayment_id() { return payment_id; }
    public void setPayment_id(String payment_id) { this.payment_id = payment_id; }

    public int getStatus_code() { return status_code; }
    public void setStatus_code(int status_code) { this.status_code = status_code; }

    public String getStatus_message() { return status_message; }
    public void setStatus_message(String status_message) { this.status_message = status_message; }

    public String getFirst_name() { return first_name; }
    public void setFirst_name(String first_name) { this.first_name = first_name; }

    public String getLast_name() { return last_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
