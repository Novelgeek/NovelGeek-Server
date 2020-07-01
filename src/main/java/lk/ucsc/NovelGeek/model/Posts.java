package lk.ucsc.NovelGeek.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "Posts")
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postid;
    private String title;
    private Date publishedDate;
    private String imagePath;
    private String description;
    private String sharedtype;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "id")
    Users users;

    @JsonIgnoreProperties
    @OneToMany(targetEntity = PostsLikes.class, mappedBy = "posts", cascade = CascadeType.ALL)
    Set<PostsLikes> postslikes;

    @JsonIgnoreProperties
    @OneToMany(targetEntity = PostsComments.class, mappedBy = "posts", cascade = CascadeType.ALL)
    Set<PostsComments> postscomments;

    public long getPostid() { return postid; }

    public String getTitle() { return title; }

    public Date getPublishedDate() { return publishedDate; }

    public String getImagePath() { return imagePath; }

    public String getDescription() { return description; }

    public String getSharedtype() { return sharedtype; }

    public Users getUsers() { return users; }

    public Set<PostsLikes> getPostslikes() { return postslikes; }

    public void setPostid(long postid) { this.postid = postid; }

    public void setTitle(String title) { this.title = title; }

    public void setPublishedDate(Date publishedDate) { this.publishedDate = publishedDate; }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public void setDescription(String description) { this.description = description; }

    public void setSharedtype(String sharedtype) { this.sharedtype = sharedtype; }

    public void setUsers(Users users) { this.users = users; }

    public void setPostslikes(Set<PostsLikes> postslikes) { this.postslikes = postslikes; }

    public Set<PostsComments> getPostscomments() { return postscomments; }

    public void setPostscomments(Set<PostsComments> postscomments) { this.postscomments = postscomments; }
}
