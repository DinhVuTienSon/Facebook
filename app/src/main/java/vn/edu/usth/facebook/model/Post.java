package vn.edu.usth.facebook.model;


import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

import com.google.firebase.database.Exclude;

import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Post {
    private String TAG = "POST MODEL";
    private String post_id;
    private String author_id;
    private String author_image;
    private String author_name;
    private Long post_date;
    private String post_description;
    private String post_image;
    private String post_likes;
    private String post_comments;

    //constructors
    public Post(){
    }

    public Post(String post_id) {
        this.post_id = post_id;
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

    public Long getPost_date() {
        return post_date;
    }

    public void setPost_date(Long post_date) {
        this.post_date = post_date;
    }

    public String getPost_description() {
        return post_description;
    }

    public void setPost_description(String post_description) {
        this.post_description = post_description;
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

    //    only use if the post_id is from FirebaseDatabase
    public String getActual_post_id(){
        String p_id = post_id.substring(0,20);;
//        String p_id =
        Log.i(TAG, "ID: " + p_id);
        return p_id;
    }

//    only use if the post_id is from FirebaseDatabase
    public void getAuthor_ID_from_db(){
        try{
            String author;
            author = post_id.substring(21,post_id.length());;
//            author =

            this.setAuthor_id(author);
        }catch(Exception e){
            Log.e(TAG, "GET AUTHOR ID ERROR: " + this.getAuthor_id());
        }
    }

    public Date getActual_date(){
        Date date = new Date(post_date);
        return date;
    }

    public String date_status(){
        Long time_diff = System.currentTimeMillis() - post_date;
        if(time_diff < 0){
            return "impossible time travel lmaoooo";
        }
        else if (time_diff < TimeUnit.MINUTES.toMillis(1)) {
            return "Just now";
        }
        else if (time_diff < TimeUnit.HOURS.toMillis(1)) {
            long minutesAgo = TimeUnit.MILLISECONDS.toMinutes(time_diff);
            return minutesAgo + (minutesAgo == 1 ? " minute ago" : " minutes ago");
        } else if (time_diff < TimeUnit.DAYS.toMillis(1)) {
            long hoursAgo = TimeUnit.MILLISECONDS.toHours(time_diff);
            return hoursAgo + (hoursAgo == 1 ? " hour ago" : " hours ago");
        } else {
            // More than 24 hours, convert to "mm dd, yyyy" format
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM - dd, yyyy");
            String formattedDate = dateFormat.format(new Date(post_date));

            // Convert numerical month to words
            String[] parts = formattedDate.split(" - ");
            int month_n = Integer.parseInt(parts[0]);
            String month_w = convertMonth(month_n);

            return month_w + " " + parts[1];  // Combine month in words with the rest of the formatted date
        }
    }
    public String convertMonth(int n){
        String month[] = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
        
        if (n < 1 || n > 12) {
            throw new IllegalArgumentException("Invalid month number");
        }

        return month[n - 1];
    }
}
