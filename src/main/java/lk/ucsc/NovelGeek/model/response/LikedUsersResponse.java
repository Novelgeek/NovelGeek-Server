package lk.ucsc.NovelGeek.model.response;

public class LikedUsersResponse {
    private String likedUser;
    private String imagePath;

    public String getLikedUser() { return likedUser; }
    public String getImagePath() { return imagePath; }

    public void setLikedUser(String likedUser) { this.likedUser = likedUser; }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
