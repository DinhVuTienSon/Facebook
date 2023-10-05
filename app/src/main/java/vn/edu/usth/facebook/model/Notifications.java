package vn.edu.usth.facebook.model;

public class Notifications {

    private String notificationAva;
    private String notificationContent;
    private String notificationTime;

    public String getNotificationAva() {
        return notificationAva;
    }

    public void setNotificationAva(String notificationAva) {
        this.notificationAva = notificationAva;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public Notifications(String notificationAva, String notificationContent, String notificationTime) {
        this.notificationAva = notificationAva;
        this.notificationContent = notificationContent;
        this.notificationTime = notificationTime;
    }
}
