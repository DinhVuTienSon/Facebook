package vn.edu.usth.facebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.facebook.adapter.SearchAdapter;
import vn.edu.usth.facebook.model.Users;


//TODO: function get avatar

public class SearchActivity extends AppCompatActivity {
    private RecyclerView search_recyclerView;
    private List<Users> users;
    private SearchAdapter searchAdapter;
    private AutoCompleteTextView search_bar;

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
        search_recyclerView.setHasFixedSize(true);
        search_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        search_bar = findViewById(R.id.search_bar);
        users = new ArrayList<>();
        searchAdapter = new SearchAdapter(users, this);
        search_recyclerView.setAdapter(searchAdapter);

        readUsers();

        //gõ chữ j thì hiện user's first name theo chữ đấy
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUser(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


//        for (int i = 0; i < 15; i++) {
//            String search_ava = "https://picsum.photos/600/300?random&" + i;
//            String search_name = "ST";
//            String search_info = "Live in Hanoi, Vietnam";
//
//            Users user = new Users(search_ava, search_name, search_info,"");
//            users.add(user);
//        }
//        SearchAdapter adapter = new SearchAdapter(users, SearchActivity.this);
//        RecyclerView recyclerView = findViewById(R.id.search_recyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Navigate back to the previous activity
        return true;
    }

    //display all user in firebase into recyclerView before enter any word into search bar
    private void readUsers () {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (TextUtils.isEmpty(search_bar.getText().toString())) {
                    users.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Users user = snapshot.getValue(Users.class);
                        users.add(user);
                    }
                    searchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error if needed
            }
        });
    }

    //search user from firebase
    private void searchUser(String s) {
        search_recyclerView.setVisibility(View.VISIBLE);

        Query query = FirebaseDatabase.getInstance().getReference().child("users")
                .orderByChild("first_name")
                .startAt(s)
                .endAt(s + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Users user = snapshot.getValue(Users.class);
                    users.add(user);
                }

                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error if needed
            }
        });
    }
}