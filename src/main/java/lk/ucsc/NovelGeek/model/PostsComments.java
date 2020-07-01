package lk.ucsc.NovelGeek.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity(name="PostsComments")
public class PostsComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentid;

    private String comment;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="id")
    Users users;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "postid")
    Posts posts;

    public Long getCommentid() { return commentid; }

    public void setCommentid(Long commentid) { this.commentid = commentid; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public Users getUsers() { return users; }

    public void setUsers(Users users) { this.users = users; }

    public Posts getPosts() { return posts; }

    public void setPosts(Posts posts) { this.posts = posts; }
}
