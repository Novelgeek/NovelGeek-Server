package lk.ucsc.NovelGeek.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity(name="PostNotifications")
public class PostNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notificationid;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "id")
    private Users user;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "id")
    private Users commentor;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "id")
    private Users replier;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "postid")
    private Posts post;

    private String notificationType;
    private Date date;
    private String postTitle;

    public long getNotificationid() { return notificationid; }
    public void setNotificationid(long notificationid) { this.notificationid = notificationid; }

    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }

    public Users getCommentor() { return commentor; }
    public void setCommentor(Users commentor) { this.commentor = commentor; }

    public Users getReplier() { return replier; }
    public void setReplier(Users replier) { this.replier = replier; }

    public Posts getPost() { return post; }
    public void setPost(Posts post) { this.post = post; }

    public String getNotificationType() { return notificationType; }
    public void setNotificationType(String notificationType) { this.notificationType = notificationType; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getPostTitle() { return postTitle; }
    public void setPostTitle(String postTitle) { this.postTitle = postTitle; }
}
