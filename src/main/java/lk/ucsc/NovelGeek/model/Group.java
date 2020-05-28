package lk.ucsc.NovelGeek.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "Groups")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "members"})
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupId;

    private String groupName;

    private Date createdOn;

    private String description;

    private String groupAvatar;

    @OneToMany(targetEntity = Members.class, mappedBy = "group")
    Set<Members> members;

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
