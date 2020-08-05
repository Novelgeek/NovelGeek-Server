package lk.ucsc.NovelGeek.model.group;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.ucsc.NovelGeek.model.Posts;

import javax.persistence.*;

@Entity(name = "group_posts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "group"})
public class GroupPosts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupPostId;


    @ManyToOne()
    @JoinColumn(name = "postid")
    Posts posts;

    @ManyToOne()
    @JoinColumn(name = "groupId")
    Group group;

    public long getGroupPostId() {
        return groupPostId;
    }

    public void setGroupPostId(long groupPostId) {
        this.groupPostId = groupPostId;
    }

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
