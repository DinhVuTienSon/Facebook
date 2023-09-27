package vn.edu.usth.facebook.model;

public class Friends_recommend {
    private String friend_rec_ava;
    private String friend_rec_name;
    private String mutual_friends_rec;

    public String getFriendRecAva() {
        return friend_rec_ava;
    }

    public void setFriendRecAva(String friend_rec_ava) {
        this.friend_rec_ava = friend_rec_ava;
    }

    public String getFriendRecName() {
        return friend_rec_name;
    }

    public void setFriendRecName(String friend_rec_name) {
        this.friend_rec_name = friend_rec_name;
    }

    public String getMutualFriends_rec() {
        return mutual_friends_rec;
    }

    public void setMutualFriends_rec(String mutual_friends_rec) {this.mutual_friends_rec = mutual_friends_rec; }

    public Friends_recommend(String friend_rec_ava, String friend_rec_name, String mutual_friends_rec){
        this.friend_rec_ava = friend_rec_ava;
        this.friend_rec_name = friend_rec_name;
        this.mutual_friends_rec = mutual_friends_rec;
    }
}
