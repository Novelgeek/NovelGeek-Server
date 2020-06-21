package lk.ucsc.NovelGeek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity(name="Users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "members", "myNotifications", "notiFiredByMe", "password", "provider", "providerId", "role"})
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String role;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    private String password;
    private String imageUrl;
    private String provider;
    private String providerId;
    private boolean isVerified;


    @OneToMany(targetEntity = Members.class, mappedBy = "users")
    Set<Members> members;

    @OneToMany(targetEntity = Notification.class, mappedBy = "targetUser")
    Set<Members> myNotifications;

    @JsonIgnoreProperties
    @OneToMany(targetEntity = Posts.class, mappedBy = "users",cascade = CascadeType.ALL)
    Set<Posts> posts;

    @JsonIgnoreProperties
    @OneToMany(targetEntity = PostsLikes.class, mappedBy = "users")
    Set<PostsLikes> postslikes;

    public Set<Members> getMyNotifications() {
        return myNotifications;
    }

    public void setMyNotifications(Set<Members> myNotifications) {
        this.myNotifications = myNotifications;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Members> getMembers() {
        return members;
    }

    public void setMembers(Set<Members> members) {
        this.members = members;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public void setPosts(Set<Posts> posts) { this.posts = posts; }

    public Set<Posts> getPosts() { return posts; }

    public void setPostslikes(Set<PostsLikes> postslikes) { this.postslikes = postslikes; }

    public Set<PostsLikes> getPostslikes() { return postslikes; }
}
