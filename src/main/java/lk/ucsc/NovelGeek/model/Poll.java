package lk.ucsc.NovelGeek.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pollid;

    private String title;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "id")
    private Users user;

    private Boolean visible;

    public Poll(){
    }

    public Poll(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPollid() {
        return pollid;
    }

    public void setPollid(Long pollid) {
        this.pollid = pollid;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }



}
