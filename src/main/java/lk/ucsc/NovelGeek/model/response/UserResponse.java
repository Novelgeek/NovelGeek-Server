package lk.ucsc.NovelGeek.model.response;

import lk.ucsc.NovelGeek.model.Users;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String imageUrl;

    public UserResponse() {
    }

    public UserResponse(Users users) {
        this.id = users.getId();
        this.username = users.getUsername();
        this.email = users.getEmail();
        this.imageUrl = users.getImageUrl();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
