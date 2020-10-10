package lk.ucsc.NovelGeek.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

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
    private Date paidDate;

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

    public Date getPaidDate() { return paidDate; }
    public void setPaidDate(Date paidDate) { this.paidDate = paidDate; }
}
