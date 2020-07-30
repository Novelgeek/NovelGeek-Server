package lk.ucsc.NovelGeek.dto;

import lk.ucsc.NovelGeek.model.group.Members;
import lk.ucsc.NovelGeek.model.Users;

import java.util.Set;

public class GroupUsersDto {
    private long userId;

    private String username;

    private String email;

    private boolean isMember;

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public GroupUsersDto() {
    }

    public GroupUsersDto(Users user, Set<Members> members) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.imageUrl = user.getImageUrl();
        members.forEach(member -> {
            if (member.getUsers().getId() == user.getId()){
                this.isMember = true;
            }
        });
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }
}
