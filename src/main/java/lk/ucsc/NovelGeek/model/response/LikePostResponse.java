package lk.ucsc.NovelGeek.model.response;

public class LikePostResponse {

    private  boolean isLiked;
    private boolean isExist;

    public boolean isLiked() { return isLiked; }

    public boolean isExist() { return isExist; }

    public void setLiked(boolean liked) { isLiked = liked; }

    public void setExist(boolean exist) { isExist = exist; }
}
