package vn.edu.usth.facebook.model;

public class Comments {
    private String comment_id;
    private String comment_ava;
    private String comment_name;
    private String comment_content;
    private String author;
    private String comment;

//    constructors

    public Comments(){

    }

    public Comments(String comment_ava, String comment_name, String comment_content) {
        this.comment_ava = comment_ava;
        this.comment_name = comment_name;
        this.comment_content = comment_content;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_ava() {
        return comment_ava;
    }

    public void setComment_ava(String comment_ava) {
        this.comment_ava = comment_ava;
    }

    public String getComment_name() {
        return comment_name;
    }

    public void setComment_name(String comment_name) {
        this.comment_name = comment_name;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}