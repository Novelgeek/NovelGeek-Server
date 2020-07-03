package lk.ucsc.NovelGeek.model.response;

public class BasicStat {
    private int noOfUsers;
    private int noOfGroups;
    private int noOfPosts;
    private int noOfAdmins;

    public BasicStat() {
    }

    public int getNoOfUsers() {
        return noOfUsers;
    }

    public int getNoOfAdmins() {
        return noOfAdmins;
    }

    public void setNoOfAdmins(int noOfAdmins) {
        this.noOfAdmins = noOfAdmins;
    }

    public void setNoOfUsers(int noOfUsers) {
        this.noOfUsers = noOfUsers;
    }

    public int getNoOfGroups() {
        return noOfGroups;
    }

    public void setNoOfGroups(int noOfGroups) {
        this.noOfGroups = noOfGroups;
    }

    public int getNoOfPosts() {
        return noOfPosts;
    }

    public void setNoOfPosts(int noOfPosts) {
        this.noOfPosts = noOfPosts;
    }
}
