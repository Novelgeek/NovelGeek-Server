package lk.ucsc.NovelGeek.dto;

import lk.ucsc.NovelGeek.model.Posts;
import lk.ucsc.NovelGeek.model.group.GroupPosts;
import lk.ucsc.NovelGeek.model.group.Members;
import lk.ucsc.NovelGeek.model.response.PostResponse;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class GroupDetailedDto {
    private long groupId;

    private String groupName;

    private Date createdOn;

    private String description;

    private String groupAvatar;

    Set<Members> members;

    List<PostResponse> posts;

    private boolean isMember;

    private boolean isAdmin;

    public GroupDetailedDto() {
    }


    public List<PostResponse> getPosts() {
        return posts;
    }

    public void setPosts(List<PostResponse> posts) {
        this.posts = posts;
    }

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

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
