package lk.ucsc.NovelGeek.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private long reviewId;
    @Column(columnDefinition="TEXT")
    private String reviewDescription;

    @ManyToOne
    @JoinColumn(name="users_id")
    private Users userId;

    private String bookId;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments = new ArrayList<Comment>();

    private Date timestamp;

    private long noOfLikes;
    @Transient
    private String reviewAge;

    public Review() {
    }

    public Review(String reviewDescription, Users userId, String bookId, long noOfLikes) {
        this.reviewDescription = reviewDescription;
        this.userId = userId;
        this.bookId = bookId;
        this.noOfLikes = noOfLikes;
        this.timestamp = new Date();

    }

    public long getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(long noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getReviewAge() {
        return reviewAge;
    }

    public void setReviewAge(String reviewAge) {
        this.reviewAge = reviewAge;
    }
}
