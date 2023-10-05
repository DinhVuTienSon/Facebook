package vn.edu.usth.facebook.model;

public class Users_friends {
    private String user_friend_ava;
    private String user_friend_name;

    public String getUser_friend_ava() {
        return user_friend_ava;
    }

    public void setUser_friend_ava(String user_friend_ava) {
        this.user_friend_ava = user_friend_ava;
    }

    public String getUser_friend_name() {
        return user_friend_name;
    }

    public void setUser_friend_name(String user_friend_name) {
        this.user_friend_name = user_friend_name;
    }

    public Users_friends(String user_friend_ava, String user_friend_name) {
        this.user_friend_ava = user_friend_ava;
        this.user_friend_name = user_friend_name;
    }
}
