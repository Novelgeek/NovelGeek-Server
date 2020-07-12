package lk.ucsc.NovelGeek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.ucsc.NovelGeek.model.book.BookRating;
import lk.ucsc.NovelGeek.model.book.LocalBook;
import lk.ucsc.NovelGeek.model.book.RecentlyViewed;
import lk.ucsc.NovelGeek.model.group.Members;

import javax.persistence.*;
import java.util.Set;

@Entity(name="Users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "members",
                        "myNotifications", "notiFiredByMe", "password", "provider", "providerId",
                        "role", "friends", "verified", "recentlyViewed", "bookRatings"})
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


    @OneToMany(targetEntity = Friends.class, mappedBy = "user1")
    Set<Friends> friends;

    @OneToMany(targetEntity = BookRating.class, mappedBy = "user")
    Set<BookRating> bookRatings;


    @OneToMany(targetEntity = RecentlyViewed.class, mappedBy = "user")
    Set<BookRating> recentlyViewed;


    @JsonIgnoreProperties
    @OneToMany(targetEntity = Posts.class, mappedBy = "users",cascade = CascadeType.ALL)
    Set<Posts> posts;

    @JsonIgnoreProperties
    @OneToMany(targetEntity = PostsLikes.class, mappedBy = "users")
    Set<PostsLikes> postslikes;

    @JsonIgnoreProperties
    @OneToMany(targetEntity = PostsComments.class, mappedBy = "users")
    Set<PostsComments> postscomments;

    @JsonIgnoreProperties
    @OneToMany(targetEntity = LocalBook.class, mappedBy = "users")
    Set<LocalBook> localBooks;

    public Set<BookRating> getRecentlyViewed() {
        return recentlyViewed;
    }

    public void setRecentlyViewed(Set<BookRating> recentlyViewed) {
        this.recentlyViewed = recentlyViewed;
    }

    public Set<BookRating> getBookRatings() {
        return bookRatings;
    }

    public void setBookRatings(Set<BookRating> bookRatings) {
        this.bookRatings = bookRatings;
    }

    public Set<Friends> getFriends() {
        return friends;
    }

    public void setFriends(Set<Friends> friends) {
        this.friends = friends;
    }
  
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

    public Set<PostsComments> getPostscomments() { return postscomments; }

    public void setPostscomments(Set<PostsComments> postscomments) { this.postscomments = postscomments; }
}
