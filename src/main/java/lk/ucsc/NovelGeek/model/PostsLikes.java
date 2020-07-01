package lk.ucsc.NovelGeek.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity(name="PostsLikes")
public class PostsLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long columnid;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="id")
    Users users;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "postid")
    Posts posts;

    public Long getColumnid() { return columnid; }

    public Users getUsers() { return users; }

    public Posts getPosts() { return posts; }

    public void setColumnid(Long columnid) { this.columnid = columnid; }

    public void setUsers(Users users) { this.users = users; }

    public void setPosts(Posts posts) { this.posts = posts; }
}
