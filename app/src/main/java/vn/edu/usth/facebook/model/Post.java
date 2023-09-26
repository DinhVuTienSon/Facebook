package vn.edu.usth.facebook.model;


public class Post {
    private String author_image;
    private String author_name;
    private String post_date;
    private String post_description;
    private String post_image;
    private String post_likes;
    private String post_comments;

    public String getAuthor_image() {
        return author_image;
    }

    public void setAuthor_image(String author_image) {
        this.author_image = author_image;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_description() {
        return post_description;
    }

    public void setPost_description(String post_description) {
        this.post_description = post_description;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getPost_likes() {
        return post_likes;
    }

    public void setPost_likes(String post_likes) {
        this.post_likes = post_likes;
    }

    public String getPost_comments() {
        return post_comments;
    }

    public void setPost_comments(String post_comments) {
        this.post_comments = post_comments;
    }

    public Post(String author_image, String author_name, String post_date,
                String post_description, String post_image, String post_likes,
                String post_comments) {
        this.author_image = author_image;
        this.author_name = author_name;
        this.post_date = post_date;
        this.post_description = post_description;
        this.post_image = post_image;
        this.post_likes = post_likes;
        this.post_comments = post_comments;
    }
}

//    public String username;
//    public String text;
//    public String photo;


//    public Post(String username, String text, String photo) {
//        this.username = username;
//        this.text = text;
//        this.photo = photo;
//    }
//
//    public String getUsername(){
//
//        return username;
//    }
//    public void setUsername(String username){
//
//        this.username = username;
//    }
//    public String getText(){
//
//        return text;
//    }
//    public void setText(String text){
//
//        this.text = text;
//    }
//    public String getPhoto(){
//
//        return photo;
//    }
//    public void setPhoto(String photo){
//
//        this.photo = photo;
//    }
//
//}
