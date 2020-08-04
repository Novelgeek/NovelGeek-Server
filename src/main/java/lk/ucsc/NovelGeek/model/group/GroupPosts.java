package lk.ucsc.NovelGeek.model.group;

import lk.ucsc.NovelGeek.model.Posts;

import javax.persistence.*;

@Entity
public class GroupPosts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupId;

    @ManyToOne()
    @JoinColumn(name = "groupId")
    Posts posts;

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }
}
