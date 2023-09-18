package vn.edu.usth.facebook.model;

// not use file

public class Profile_menu {

    private String profile_menu_ava;
    private String profile_menu_name;

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

    public Profile_menu(String profile_menu_ava, String profile_menu_name) {
        this.profile_menu_ava = profile_menu_ava;
        this.profile_menu_name = profile_menu_name;
    }
}
