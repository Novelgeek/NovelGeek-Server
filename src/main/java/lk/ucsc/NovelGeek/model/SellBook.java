package lk.ucsc.NovelGeek.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Selling")
public class SellBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sellingid;

    private String title;
    private Date publishedDate;
    private String imagePath;
    @Column(length = 2000)
    private String description;
    private String telephone;
    private String merchantid;
    private float price;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "id")
    Users users;

    @JsonIgnoreProperties
    @OneToOne(mappedBy = "sellbook")
    private Payments payments;

    public long getSellingid() { return sellingid; }
    public void setSellingid(long sellingid) { this.sellingid = sellingid; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Date getPublishedDate() { return publishedDate; }
    public void setPublishedDate(Date publishedDate) { this.publishedDate = publishedDate; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }


    public Users getUsers() { return users; }
    public void setUsers(Users users) { this.users = users; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public String getMerchantid() { return merchantid; }
    public void setMerchantid(String merchantid) { this.merchantid = merchantid; }

    public Payments getPayments() { return payments; }
    public void setPayments(Payments payments) { this.payments = payments; }
}
