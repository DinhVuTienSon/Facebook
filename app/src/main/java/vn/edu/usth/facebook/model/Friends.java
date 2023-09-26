package vn.edu.usth.facebook.model;

public class Friends {
    private String friend_req_ava;
    private String friend_req_name;
    private String req_date;
    private String mutual_friends;

    public String getFriend_req_ava() {
        return friend_req_ava;
    }

    public void setFriend_req_ava(String friend_req_ava) {
        this.friend_req_ava = friend_req_ava;
    }

    public String getFriend_req_name() {
        return friend_req_name;
    }

    public void setFriend_req_name(String friend_req_name) {
        this.friend_req_name = friend_req_name;
    }

    public String getReq_date() {
        return req_date;
    }

    public void setReq_date(String req_date) {
        this.req_date = req_date;
    }

    public String getMutual_friends() {
        return mutual_friends;
    }

    public void setMutual_friends(String mutual_friends) {this.mutual_friends = mutual_friends; }

    public Friends(String friend_req_ava, String friend_req_name, String req_date, String mutual_friends){
        this.friend_req_ava = friend_req_ava;
        this.friend_req_name = friend_req_name;
        this.req_date = req_date;
        this.mutual_friends = mutual_friends;
    }
}
