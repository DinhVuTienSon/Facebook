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
    private String user_background;
    private String user_bio;
    private String user_hobbies;
    private String user_links;
    private String user_live_in;
    private String user_work;
    private String user_education;
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

    public Users(){
    }

    //add user_new_infos to a map
    //add new user infos
    @Exclude
    public Map<String,Object> toNewMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("first_name", this.first_name);
        result.put("sur_name", this.sur_name);
        result.put("email", this.email);

        return result;
    }
    //add user_bio to map
    public Map<String,Object> toBioMap(String type){
        HashMap<String,Object> result = new HashMap<>();
        result.put("user_" + type, this.user_bio);
        return result;
    }

    //add user_hobbies to map
    public Map<String,Object> toHobbiesMap(String type){
        HashMap<String,Object> result = new HashMap<>();
        result.put("user_" + type, this.user_hobbies);
        return result;
    }

    //add user_links to map
    public Map<String,Object> toLinksMap(String type){
        HashMap<String,Object> result = new HashMap<>();
        result.put("user_" + type, this.user_links);
        return result;
    }
    //add user details to map
    public Map<String,Object> toDetailsMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("user_live_in", this.user_live_in);
        result.put("user_work", this.user_work);
        result.put("user_education", this.user_education);
        return result;
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

    public String getUser_background() {
        return user_background;
    }

    public void setUser_background(String user_background) {
        this.user_background = user_background;
    }

    public String getUser_bio() {
        return user_bio;
    }

    public void setUser_bio(String user_bio) {
        this.user_bio = user_bio;
    }

    public String getUser_hobbies() {
        return user_hobbies;
    }

    public void setUser_hobbies(String user_hobbies) {
        this.user_hobbies = user_hobbies;
    }

    public String getUser_links() {
        return user_links;
    }

    public void setUser_links(String user_links) {
        this.user_links = user_links;
    }

    public String getUser_live_in() {
        return user_live_in;
    }

    public void setUser_live_in(String user_live_in) {
        this.user_live_in = user_live_in;
    }

    public String getUser_work() {
        return user_work;
    }

    public void setUser_work(String user_work) {
        this.user_work = user_work;
    }

    public String getUser_education() {
        return user_education;
    }

    public void setUser_education(String user_education) {
        this.user_education = user_education;
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