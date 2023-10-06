package vn.edu.usth.facebook.model;

public class Notifications {

    private String notification_ava;
    private String notification_content;
    private String notification_user_name;
    private String userId;
    private String text;
    private String postId;
    private boolean isPost;

    public Notifications() {
    }

    public Notifications(String userId, String text, String postId, boolean isPost) {
        this.userId = userId;
        this.text = text;
        this.postId = postId;
        this.isPost = isPost;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public boolean isPost() {
        return isPost;
    }

    public void setPost(boolean post) {
        isPost = post;
    }
//  constructors

    public Notifications(String notification_ava, String notification_content, String notification_user_name) {
        this.notification_ava = notification_ava;
        this.notification_content = notification_content;
        this.notification_user_name = notification_user_name;
    }
    public String getNotification_ava() {
        return notification_ava;
    }

    public void setNotification_ava(String notification_ava) {
        this.notification_ava = notification_ava;
    }

    public String getNotification_content() {
        return notification_content;
    }

    public void setNotification_content(String notification_content) {
        this.notification_content = notification_content;
    }

    public String getNotification_user_name() {
        return notification_user_name;
    }

    public void setNotification_user_name(String notification_user_name) {
        this.notification_user_name = notification_user_name;
    }
}