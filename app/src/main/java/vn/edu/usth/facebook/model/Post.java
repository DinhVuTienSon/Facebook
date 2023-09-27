package vn.edu.usth.facebook.model;


public class Post {

    private String author_image;
    private String author_name;
    private String post_date;
    private String post_description;
    private String post_image;
    private String post_likes;
    private String post_comments;

    public String getAuthorImage() {
        return author_image;
    }

    public void setAuthorImage(String author_image) {
        this.author_image = author_image;
    }

    public String getAuthorName() {
        return author_name;
    }

    public void setAuthorName(String author_name) {
        this.author_name = author_name;
    }

    public String getPostDate() {
        return post_date;
    }

    public void setPostDate(String post_date) {
        this.post_date = post_date;
    }

    public String getPostDescription() {
        return post_description;
    }

    public void setPostDescription(String post_description) {
        this.post_description = post_description;
    }

    public String getPostImage() {
        return post_image;
    }

    public void setPostImage(String post_image) {
        this.post_image = post_image;
    }

    public String getPostLikes() {
        return post_likes;
    }

    public void setPostLikes(String post_likes) {
        this.post_likes = post_likes;
    }

    public String getPostComments() {
        return post_comments;
    }

    public void setPostComments(String post_comments) {
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
