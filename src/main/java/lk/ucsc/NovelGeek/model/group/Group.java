package lk.ucsc.NovelGeek.model.group;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "Groups")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "members"})
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "groupId")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupId;

    private String groupName;

    private Date createdOn;

    private String description;

    private String groupAvatar;

    @JsonManagedReference
    @OneToMany(targetEntity = Members.class, mappedBy = "group", cascade = CascadeType.ALL)
    Set<Members> members;

    @JsonManagedReference
    @OneToMany(targetEntity = GroupPosts.class, mappedBy = "group", cascade = CascadeType.ALL)
    Set<GroupPosts> posts;

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getDescription() {
        return description;
    }

    public Set<GroupPosts> getPosts() {
        return posts;
    }

    public void setPosts(Set<GroupPosts> posts) {
        this.posts = posts;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupAvatar() {
        return groupAvatar;
    }

    public void setGroupAvatar(String groupAvatar) {
        this.groupAvatar = groupAvatar;
    }

    public Set<Members> getMembers() {
        return members;
    }

    public void setMembers(Set<Members> members) {
        this.members = members;
    }
}
