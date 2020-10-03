package lk.ucsc.NovelGeek.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Optional;

@Entity(name="CommentReplies")
public class CommentReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long replyid;

    private String comment;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="commentid")
    PostsComments postscomments;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="id")
    Users users;

    public long getReplyid() { return replyid; }
    public void setReplyid(long replyid) { this.replyid = replyid; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Users getUsers() { return users; }
    public void setUsers(Users users) { this.users = users; }

    public PostsComments getPostscomments() { return postscomments; }
    public void setPostscomments(PostsComments postscomments) { this.postscomments = postscomments; }
}
