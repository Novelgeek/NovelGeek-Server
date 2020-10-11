package lk.ucsc.NovelGeek.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lk.ucsc.NovelGeek.model.group.GroupPosts;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "Posts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "groupPosts"})
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postid;
    private String title;
    private Date publishedDate;
    private String imagePath;

    @Column(length = 3000)
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
    @OneToMany(targetEntity = PostsReports.class, mappedBy = "posts", cascade = CascadeType.ALL)
    Set<PostsReports> postsreports;

    @JsonIgnoreProperties
    @OneToMany(targetEntity = PostsComments.class, mappedBy = "posts", cascade = CascadeType.ALL)
    Set<PostsComments> postscomments;

    @JsonIgnoreProperties
    @OneToMany(targetEntity = PostNotification.class, mappedBy = "post", cascade = CascadeType.ALL)
    Set<PostNotification> postNotifications;

    @OneToMany(targetEntity = GroupPosts.class, mappedBy = "posts")
    Set<GroupPosts> groupPosts;

    public Set<GroupPosts> getGroupPosts() {
        return groupPosts;
    }

    public void setGroupPosts(Set<GroupPosts> groupPosts) {
        this.groupPosts = groupPosts;
    }

    public long getPostid() { return postid; }
    public String getTitle() { return title; }
    public Date getPublishedDate() { return publishedDate; }
    public String getImagePath() { return imagePath; }
    public String getDescription() { return description; }
    public String getSharedtype() { return sharedtype; }
    public Users getUsers() { return users; }
    public Set<PostsReports> getPostsreports() { return postsreports; }
    public Set<PostNotification> getPostNotifications() { return postNotifications; }

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
    public void setPostsreports(Set<PostsReports> postsreports) { this.postsreports = postsreports; }
    public void setPostNotifications(Set<PostNotification> postNotifications) { this.postNotifications = postNotifications; }
}
