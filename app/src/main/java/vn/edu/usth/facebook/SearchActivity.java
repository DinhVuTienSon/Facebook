package vn.edu.usth.facebook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import vn.edu.usth.facebook.adapter.CommentAdapter;
import vn.edu.usth.facebook.adapter.SearchAdapter;
import vn.edu.usth.facebook.model.Comments;
import vn.edu.usth.facebook.model.Search;
import vn.edu.usth.facebook.model.Users;

//TODO: function to search other users by name

public class SearchActivity extends AppCompatActivity {
    private RecyclerView search_recyclerView;
    private ArrayList<Search> search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // Enable the back arrow
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigate back to the previous activity
            }
        });


        search_recyclerView = findViewById(R.id.search_recyclerView);
        search = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            String search_ava = "https://picsum.photos/600/300?random&" + i;
            String search_name = "ST";
            String search_info = "Live in Hanoi, Vietnam";

            Search searches = new Search(search_ava, search_name, search_info);
            search.add(searches);
        }
            SearchAdapter adapter = new SearchAdapter(search, SearchActivity.this);
            RecyclerView recyclerView = findViewById(R.id.search_recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Navigate back to the previous activity
        return true;
    }
}