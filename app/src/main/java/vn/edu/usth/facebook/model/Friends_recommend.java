package vn.edu.usth.facebook.model;

public class Friends_recommend {
    private String friendRecAva;
    private String friendRecName;
    private String mutualFriends_rec;

    public String getFriendRecAva() {
        return friendRecAva;
    }

    public void setFriendRecAva(String friendRecAva) {
        this.friendRecAva = friendRecAva;
    }

    public String getFriendRecName() {
        return friendRecName;
    }

    public void setFriendRecName(String friendRecName) {
        this.friendRecAva = friendRecName;
    }

    public String getMutualFriends_rec() {
        return mutualFriends_rec;
    }

    public void setMutualFriends_rec(String mutualFriends_rec) {this.mutualFriends_rec = mutualFriends_rec; }

    public Friends_recommend(String friendRecAva, String friendRecName, String mutualFriends_rec){
        this.friendRecAva = friendRecAva;
        this.friendRecName = friendRecName;
        this.mutualFriends_rec = mutualFriends_rec;
    }
}
