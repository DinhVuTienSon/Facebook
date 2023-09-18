package vn.edu.usth.facebook.model;

public class Friends {
    private String friendReqAva;
    private String friendReqName;
    private String reqDate;
    private String mutualFriends;

    public String getFriendReqAva() {
        return friendReqAva;
    }

    public void setFriendReqAva(String friendReqAva) {
        this.friendReqAva = friendReqAva;
    }

    public String getFriendReqName() {
        return friendReqName;
    }

    public void setFriendReqName(String friendReqName) {
        this.friendReqName = friendReqName;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getMutualFriends() {
        return mutualFriends;
    }

    public void setMutualFriends(String mutualFriends) {this.mutualFriends = mutualFriends; }

    public Friends(String friendReqAva, String friendReqName, String reqDate, String mutualFriends){
        this.friendReqAva = friendReqAva;
        this.friendReqName = friendReqName;
        this.reqDate = reqDate;
        this.mutualFriends = mutualFriends;
    }
}
