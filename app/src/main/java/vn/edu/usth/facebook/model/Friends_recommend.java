package vn.edu.usth.facebook.model;

public class Friends_recommend {
    private String friend_rec_ava;
    private String friend_rec_name;
    private String mutual_friends_rec;

    public String getFriend_rec_ava() {
        return friend_rec_ava;
    }

    public void setFriend_rec_ava(String friend_rec_ava) {
        this.friend_rec_ava = friend_rec_ava;
    }

    public String getFriend_rec_name() {
        return friend_rec_name;
    }

    public void setFriend_rec_name(String friend_rec_name) {
        this.friend_rec_ava = friend_rec_name;
    }

    public String getMutual_friends_rec() {
        return mutual_friends_rec;
    }

    public void setMutual_friends_rec(String mutual_friends_rec) {this.mutual_friends_rec = mutual_friends_rec; }

    public Friends_recommend(String friend_rec_ava, String friend_rec_name, String mutual_friends_rec){
        this.friend_rec_ava = friend_rec_ava;
        this.friend_rec_name = friend_rec_name;
        this.mutual_friends_rec = mutual_friends_rec;
    }
}
