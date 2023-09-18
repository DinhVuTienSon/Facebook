package vn.edu.usth.facebook.model;


public class Post {
    private String authorImage;
    private String authorName;
    private String postDate;
    private String postDescription;
    private String postImage;
    private String postLikes;
    private String postComments;

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(String postLikes) {
        this.postLikes = postLikes;
    }

    public String getPostComments() {
        return postComments;
    }

    public void setPostComments(String postComments) {
        this.postComments = postComments;
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
