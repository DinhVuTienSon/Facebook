package vn.edu.usth.facebook.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.edu.usth.facebook.model.Notifications;
import vn.edu.usth.facebook.model.Post;

public class Users {
    private String user_id;
    private String first_name;
    private String sur_name;
    private String email;
    private String user_ava;
    private ArrayList<Post> posts;
//    private ArrayList<user_comments> comments;
    private ArrayList<Notifications> notifications;
    private ArrayList<String> user_friends;
//    constructors
    public Users(String user_id, String first_name, String sur_name, String email) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.sur_name = sur_name;
        this.email = email;
    }

//add user_infos to a map
    //add new user infos
    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("first_name", this.first_name);
        result.put("sur_name", this.sur_name);
        result.put("email", this.email);

        return result;
    }
    //add user_ava to map
    public Map<String,Object> toMap(String user_ava){
        this.toMap().put("user_ava",user_ava);
        return this.toMap();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSur_name() {
        return sur_name;
    }

    public void setSur_name(String sur_name) {
        this.sur_name = sur_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_ava() {
        return user_ava;
    }

    public void setUser_ava(String user_ava) {
        this.user_ava = user_ava;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notifications> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<String> getUser_friends() {
        return user_friends;
    }

    public void setUser_friends(ArrayList<String> user_friends) {
        this.user_friends = user_friends;
    }
}

