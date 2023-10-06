package vn.edu.usth.facebook.model;

public class Notifications {

    private String notification_ava;
    private String notification_content;
    private String notification_time;

//  constructors

    public Notifications(String notification_ava, String notification_content, String notification_time) {
        this.notification_ava = notification_ava;
        this.notification_content = notification_content;
        this.notification_time = notification_time;
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

    public String getNotification_time() {
        return notification_time;
    }

    public void setNotification_time(String notification_time) {
        this.notification_time = notification_time;
    }
}
