package lk.ucsc.NovelGeek.model.book;

import lk.ucsc.NovelGeek.model.Users;

import javax.persistence.*;
import java.util.Date;

@Entity
public class FeaturedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long featuredId;

    private Date featuredFrom;
    private Date featuredTo;

    @ManyToOne
    LocalBook localBook;

    @ManyToOne
    Users featuredBy;

    private String paymentId;

    public long getFeaturedId() {
        return featuredId;
    }

    public void setFeaturedId(long featuredId) {
        this.featuredId = featuredId;
    }

    public Date getFeaturedFrom() {
        return featuredFrom;
    }

    public void setFeaturedFrom(Date featuredFrom) {
        this.featuredFrom = featuredFrom;
    }

    public Date getFeaturedTo() {
        return featuredTo;
    }

    public void setFeaturedTo(Date featuredTo) {
        this.featuredTo = featuredTo;
    }

    public LocalBook getLocalBook() {
        return localBook;
    }

    public void setLocalBook(LocalBook localBook) {
        this.localBook = localBook;
    }

    public Users getFeaturedBy() {
        return featuredBy;
    }

    public void setFeaturedBy(Users featuredBy) {
        this.featuredBy = featuredBy;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
