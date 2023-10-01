package vn.edu.usth.facebook.model;

public class Search {
    private String search_ava;
    private String search_name;
    private String search_info;

    public String getSearch_ava() {
        return search_ava;
    }

    public void setSearch_ava(String search_ava) {
        this.search_ava = search_ava;
    }

    public String getSearch_name() {
        return search_name;
    }

    public void setSearch_name(String search_name) {
        this.search_name = search_name;
    }

    public String getSearch_info() {
        return search_info;
    }

    public void setSearch_info(String search_info) {
        this.search_info = search_info;
    }

    public Search(String search_ava, String search_name, String search_info) {
        this.search_ava = search_ava;
        this.search_name = search_name;
        this.search_info = search_info;
    }
}
