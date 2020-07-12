package lk.ucsc.NovelGeek.dto;

import lk.ucsc.NovelGeek.model.group.Group;
import lk.ucsc.NovelGeek.model.Users;

import java.io.Serializable;
import java.util.Date;

public class GroupDto implements Serializable {
    private long groupId;

    private String groupName;

    private Date createdOn;

    private String description;

    private String groupAvatar;

    private int memberCount;

    private boolean isMember;

    public GroupDto() {
    }

    public GroupDto(Group group) {
        this.groupId = group.getGroupId();
        this.groupName = group.getGroupName();
        this.createdOn = group.getCreatedOn();
        this.description = group.getDescription();
        this.groupAvatar = group.getGroupAvatar();
        this.memberCount = group.getMembers().size();
    }

    public GroupDto(Group group, Users user) {
        this.groupId = group.getGroupId();
        this.groupName = group.getGroupName();
        this.createdOn = group.getCreatedOn();
        this.description = group.getDescription();
        this.groupAvatar = group.getGroupAvatar();
        this.memberCount = group.getMembers().size();
        this.isMember = false;
        group.getMembers().forEach(members -> {
            if(members.getUsers() == user){
                this.isMember = true;
            }
        });


    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
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

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }


}
