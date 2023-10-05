package vn.edu.usth.facebook.model;

public class Comments {

    private String commentAva;
    private String commentName;
    private String commentContent;

    public String getCommentAva() {
        return commentAva;
    }

    public void setCommentAva(String commentAva) {
        this.commentAva = commentAva;
    }

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Comments(String commentAva, String commentName, String commentContent) {
        this.commentAva = commentAva;
        this.commentName = commentName;
        this.commentContent = commentContent;
    }
}
