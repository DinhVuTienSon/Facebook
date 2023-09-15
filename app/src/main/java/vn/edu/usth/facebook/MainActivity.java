package vn.edu.usth.facebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.usth.facebook.databinding.ActivityMainBinding;
import vn.edu.usth.facebook.model.Post;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ArrayList<Post> posts;
    private ProgressBar progressBar;
//    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                    replaceFragment(new HomeFragment());
//                progressBar = findViewById(R.id.idLoadingPB);
                getFacebookFeeds();
            } else if (item.getItemId() == R.id.friends) {
                replaceFragment(new FriendsFragment());
            } else if (item.getItemId() == R.id.notification) {
                replaceFragment(new NotificationFragment());
            } else {
                replaceFragment(new FbMenuFragment());
            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void getFacebookFeeds() {

        posts = new ArrayList<>();
//        mRequestQueue = Volley.newRequestQueue(MainActivity.this);
//        mRequestQueue.getCache().clear();
//        String url = "";
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

//            public void onResponse(JSONObject response) {
//                progressBar.setVisibility(View.GONE);
//                try {
//                    String authorName = response.getString("authorName");
//                    String authorImage = response.getString("authorImage");
//                    JSONArray feedsArray = response.getJSONArray("feeds");
        for (int i = 0; i < 10; i++) {
            String authorName = "ST";
            String authorImage = "https://picsum.photos/600/300?random&";
            String postDate = "now";
            String postDescription = "testing";
            String postImage = "https://picsum.photos/600/300?random&";
            String postLikes = "2";
            String postComments = "3";

//                        JSONObject feedsObj = feedsArray.getJSONObject(i);
//                        String postDate = feedsObj.getString("postDate");
//                        String postDescription = feedsObj.getString("postDescription");
//                        String postImage = feedsObj.getString("postImage");
//                        String postLikes = feedsObj.getString("postLikes");
//                        String postComments = feedsObj.getString("postComments");
            Post postModal = new Post(authorImage, authorName, postDate, postDescription, postImage, postLikes, postComments);
            posts.add(postModal);
            PostAdapter adapter = new PostAdapter(posts, MainActivity.this);
            RecyclerView recyclerView = findViewById(R.id.recyclerView);

            LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);


        }
    }
}
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(MainActivity.this, " " + error, Toast.LENGTH_SHORT).show();
//                }
//            });
//        mRequestQueue.add(jsonObjectRequest);
//    }

//    Post postModal = new Post("https://picsum.photos/600/300?random&","ST","now","testing","https://picsum.photos/600/300?random&", "2", "3");
//            posts.add(postModal);
//
//            PostAdapter adapter = new PostAdapter(posts, MainActivity.this);
//            RecyclerView recyclerView = findViewById(R.id.recyclerView);
//
//            LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
//
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setAdapter(adapter);