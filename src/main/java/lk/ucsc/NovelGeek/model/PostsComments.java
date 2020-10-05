package lk.ucsc.NovelGeek.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

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

    @JsonIgnoreProperties
    @OneToMany(targetEntity = CommentReply.class, mappedBy = "postscomments", cascade = CascadeType.ALL)
    Set<CommentReply> commentreply;

    public long getCommentid() { return commentid; }
    public void setCommentid(long commentid) { this.commentid = commentid; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Users getUsers() { return users; }
    public void setUsers(Users users) { this.users = users; }

    public Posts getPosts() { return posts; }
    public void setPosts(Posts posts) { this.posts = posts; }

    public Set<CommentReply> getCommentreply() { return commentreply; }
    public void setCommentreply(Set<CommentReply> commentreply) { this.commentreply = commentreply; }
}
