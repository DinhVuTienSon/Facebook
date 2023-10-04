package vn.edu.usth.facebook.model;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Post {
    private String post_id;
    private String author_id;
    private String author_image;
    private String author_name;
    private String post_date;
    private String post_description;
    private String post_image;
    private String post_likes;
    private String post_comments;

    //constructors

    public Post(String post_id, String author_id) {
        this.post_id = post_id;
        this.author_id = author_id;
    }

    public Post(String post_id, String author_id, String post_date, String post_description) {
        this.post_id = post_id;
        this.author_id = author_id;
        this.post_date = post_date;
        this.post_description = post_description;
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

//    add new post to map
    @Exclude
    public Map<String, Object> toNewTextMap(Map<String, String> date){
        HashMap<String,Object> result = new HashMap<>();
        result.put("post_description", this.post_description);
        result.put("post_date", date);// use Map because
        // saving on db as Map using ServerValue.TIMESTAMP and can be convert to normal date later

        return result;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

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
}
