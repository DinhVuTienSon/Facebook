package vn.edu.usth.facebook.model;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Post {
    private String postId;
    private String authorId;
    private String authorImage;
    private String authorName;
    private String postDate;
    private String postDescription;
    private String postImage;
    private String postLikes;
    private String postComments;

    //constructors

    public Post(String postId, String authorId) {
        this.postId = postId;
        this.authorId = authorId;
    }

    public Post(String postId, String authorId, String postDate, String postDescription) {
        this.postId = postId;
        this.authorId = authorId;
        this.postDate = postDate;
        this.postDescription = postDescription;
    }

    public Post(String authorImage, String authorName, String postDate,
                String postDescription, String postImage, String postLikes,
                String postComments) {
        this.authorImage = authorImage;
        this.authorName = authorName;
        this.postDate = postDate;
        this.postDescription = postDescription;
        this.postImage = postImage;
        this.postLikes = postLikes;
        this.postComments = postComments;
    }

//    add new post to map
    @Exclude
    public Map<String, Object> toNewTextMap(Map<String, String> date){
        HashMap<String,Object> result = new HashMap<>();
        result.put("post_description", this.postDescription);
        result.put("post_date", date);// use Map because
        // saving on db as Map using ServerValue.TIMESTAMP and can be convert to normal date later

        return result;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String author_image) {
        this.authorImage = author_image;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String author_name) {
        this.authorName = author_name;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String post_date) {
        this.postDate = post_date;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String post_description) {
        this.postDescription = post_description;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String post_image) {
        this.postImage = post_image;
    }

    public String getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(String post_likes) {
        this.postLikes = post_likes;
    }

    public String getPostComments() {
        return postComments;
    }

    public void setPostComments(String post_comments) {
        this.postComments = post_comments;
    }
}
