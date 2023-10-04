package vn.edu.usth.facebook.model;

// not use file

import java.util.ArrayList;

public class Profile_menu {


    private String profile_menu_ava;
    private String profile_menu_name;
    private String user_bio;
    private String user_lives;
    private String user_from;
    private String user_work;
    private String user_education;
    private ArrayList<String> user_contacts;
    private ArrayList<String> user_hobbies;
//constructors
    public Profile_menu(String profile_menu_ava, String profile_menu_name) {
        this.profile_menu_ava = profile_menu_ava;
        this.profile_menu_name = profile_menu_name;
    }

    public String getProfile_menu_ava() {
        return profile_menu_ava;
    }

    public void setProfile_menu_ava(String profile_menu_ava) {
        this.profile_menu_ava = profile_menu_ava;
    }

    public String getProfile_menu_name() {
        return profile_menu_name;
    }

    public void setProfile_menu_name(String profile_menu_name) {
        this.profile_menu_name = profile_menu_name;
    }
}
