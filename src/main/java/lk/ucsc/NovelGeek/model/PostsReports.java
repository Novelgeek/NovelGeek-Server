package lk.ucsc.NovelGeek.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity(name="PostsReports")
public class PostsReports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportid;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="id")
    Users users;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "postid")
    Posts posts;

    private String reason;

    public Users getUsers() { return users; }

    public Posts getPosts() { return posts; }

    public void setUsers(Users users) { this.users = users; }

    public void setPosts(Posts posts) { this.posts = posts; }

    public Long getReportid() { return reportid; }

    public void setReportid(Long reportid) { this.reportid = reportid; }

    public String getReason() { return reason; }

    public void setReason(String reason) { this.reason = reason; }
}
