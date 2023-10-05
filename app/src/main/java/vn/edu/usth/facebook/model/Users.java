package vn.edu.usth.facebook.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Users {
    private String userId;
    private String firstName;
    private String surName;
    private String email;
    private String userAva;
    private String userBackground;
    private String userBio;
    private String userHobbies;
    private String userLinks;
    private String userLiveIn;
    private String userWork;
    private String userEducation;
    private ArrayList<Post> posts;
//    private ArrayList<user_comments> comments;
    private ArrayList<Notifications> notifications;
    private ArrayList<String> user_friends;
//    constructors
    public Users(String userId, String firstName, String surName, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
    }

    public Users(){
    }

//add user_new_infos to a map
    //add new user infos
    @Exclude
    public Map<String,Object> toNewMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("first_name", this.firstName);
        result.put("sur_name", this.surName);
        result.put("email", this.email);

        return result;
    }
    //add user_bio to map
    public Map<String,Object> toBioMap(String type){
        HashMap<String,Object> result = new HashMap<>();
        result.put("user_" + type, this.userBio);
        return result;
    }

    //add user_hobbies to map
    public Map<String,Object> toHobbiesMap(String type){
        HashMap<String,Object> result = new HashMap<>();
        result.put("user_" + type, this.userHobbies);
        return result;
    }

    //add user_links to map
    public Map<String,Object> toLinksMap(String type){
        HashMap<String,Object> result = new HashMap<>();
        result.put("user_" + type, this.userLinks);
        return result;
    }
    //add user details to map
    public Map<String,Object> toDetailsMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("user_live_in", this.userLiveIn);
        result.put("user_work", this.userWork);
        result.put("user_education", this.userEducation);
        return result;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserAva() {
        return userAva;
    }

    public void setUserAva(String userAva) {
        this.userAva = userAva;
    }

    public String getUserBackground() {
        return userBackground;
    }

    public void setUserBackground(String userBackground) {
        this.userBackground = userBackground;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getUserHobbies() {
        return userHobbies;
    }

    public void setUserHobbies(String userHobbies) {
        this.userHobbies = userHobbies;
    }

    public String getUserLinks() {
        return userLinks;
    }

    public void setUserLinks(String userLinks) {
        this.userLinks = userLinks;
    }

    public String getUserLiveIn() {
        return userLiveIn;
    }

    public void setUserLiveIn(String userLiveIn) {
        this.userLiveIn = userLiveIn;
    }

    public String getUserWork() {
        return userWork;
    }

    public void setUserWork(String userWork) {
        this.userWork = userWork;
    }

    public String getUserEducation() {
        return userEducation;
    }

    public void setUserEducation(String userEducation) {
        this.userEducation = userEducation;
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

