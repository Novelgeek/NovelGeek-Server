package lk.ucsc.NovelGeek.model;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long commentId;

    @ManyToOne
    @JoinColumn(name="users_id")
    private Users commentedUserId;

    private  String commentDescription;
    private Date timestamp;
    @Transient
    private String commentAge;

    public Comment() {
    }

    public Comment(Users commentedUserId,String commentDescription) {
        this.commentDescription = commentDescription;
        this.commentedUserId = commentedUserId;
        this.timestamp = new Date();
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public Users getCommentedUserId() {
        return commentedUserId;
    }

    public void setCommentedUserId(Users commentedUserId) {
        this.commentedUserId = commentedUserId;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getCommentAge() {
        return commentAge;
    }

    public void setCommentAge(String commentAge) {
        this.commentAge = commentAge;
    }
}
