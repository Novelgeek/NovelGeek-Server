package lk.ucsc.NovelGeek.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long friendId;

    @ManyToOne
    @JoinColumn(name = "user1")
    Users user1;

    @ManyToOne
    @JoinColumn(name = "user2")
    Users user2;

    private String status;

    private Date dateAccepted;

    public Friends() {
    }

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }

    public Users getUser1() {
        return user1;
    }

    public void setUser1(Users user1) {
        this.user1 = user1;
    }

    public Users getUser2() {
        return user2;
    }

    public void setUser2(Users user2) {
        this.user2 = user2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateAccepted() {
        return dateAccepted;
    }

    public void setDateAccepted(Date dateAccepted) {
        this.dateAccepted = dateAccepted;
    }
}
