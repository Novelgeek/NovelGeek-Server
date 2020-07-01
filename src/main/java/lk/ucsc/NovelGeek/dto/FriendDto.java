package lk.ucsc.NovelGeek.dto;

import lk.ucsc.NovelGeek.model.Friends;
import lk.ucsc.NovelGeek.model.Users;

import javax.persistence.Column;
import java.util.Set;

public class FriendDto {
    private long id;
    private String username;
    private String email;
    private String imageUrl;
    private String status;
    private boolean isFriend;

    public FriendDto() {
    }

    public FriendDto(Friends friend) {
        this.id = friend.getUser2().getId();
        this.username = friend.getUser2().getUsername();
        this.email = friend.getUser2().getEmail();
        this.imageUrl = friend.getUser2().getImageUrl();
        this.status = friend.getStatus();
        if(friend.getStatus().equals("FRIEND")){
            this.isFriend = true;
        }
    }

    public FriendDto(Users user, Set<Friends> friends) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.imageUrl = user.getImageUrl();
        friends.forEach(friend -> {
            if (friend.getUser2() == user && friend.getStatus().equals("FRIEND")){
                this.isFriend=true;
                this.status="FRIEND";
            } else if (friend.getUser2() == user && !friend.getStatus().equals("FRIEND")) {
                this.isFriend = false;
                this.status = friend.getStatus();
            }
        });
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }
}
