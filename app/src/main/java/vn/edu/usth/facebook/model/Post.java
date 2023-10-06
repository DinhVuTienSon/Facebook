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
    private String postDate; // dummy var
    private String post_description;
    private String post_image;
    private Boolean post_likes;
    private String post_comments;


    //constructors
    public Post(){
    }

    public Post(String post_id) {
        this.post_id = post_id;
    }
    public Post(String postId, String authorId) {
        this.post_id = postId;
        this.author_id = authorId;
    }

    public Post(String postId, String authorId, String postDate, String postDescription) {
        this.post_id = postId;
        this.author_id = authorId;
        this.postDate = postDate;
        this.post_description = postDescription;
    }

    public Post(String author_image, String author_name, String postDate, String post_description, String post_image, Boolean post_likes, String post_comments) {
        this.author_image = author_image;
        this.author_name = author_name;
        this.postDate = postDate;
        this.post_description = post_description;
        this.post_image = post_image;
        this.post_likes = post_likes;
        this.post_comments = post_comments;
    }

    //    add new post to map
    @Exclude
    public Map<String, Object> toNewTextMap(Map<String, String> date){
        HashMap<String,Object> result = new HashMap<>();
//        HashMap<String,Object> decoy_likes = new HashMap<>();
//        HashMap<String,Object> decoy_comments = new HashMap<>();
//
//        decoy_likes.put("decoy", false);
//        decoy_comments.put("decoy",false);

        result.put("post_description", this.post_description);
        result.put("post_date", date);// use Map because
//        result.put("post_likes", decoy_likes);
//        result.put("post_comments", decoy_comments);
        // saving on db as Map using ServerValue.TIMESTAMP and can be convert to normal date later

        return result;
    }

    @Exclude
    public Map<String, Object> toLikesMap(String user_id){
        HashMap<String,Object> result = new HashMap<>();
        result.put(user_id, this.post_likes);

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

    public Long getPost_date() {
        return post_date;
    }

    public void setPost_date(Long post_date) {
        this.post_date = post_date;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
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

    public Boolean getPost_likes() {
        return post_likes;
    }

    public void setPost_likes(Boolean post_likes) {
        this.post_likes = post_likes;
    }

    public String getPost_comments() {
        return post_comments;
    }

    public void setPost_comments(String post_comments) {
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
