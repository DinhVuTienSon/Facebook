package vn.edu.usth.facebook.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.EditProfileActivity;
import vn.edu.usth.facebook.UploadPostActivity;
import vn.edu.usth.facebook.adapter.PostAdapter;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.model.Post;

//TODO: function to call current user ava (next to "what's on your mind")
//TODO: function to call posts info: author name, ava; post date, description, image, like, comment

public class HomeFragment extends Fragment {
    public String TAG = "HOME FRAGMENT"; //for debugging
    public CircleImageView home_ava;
    public TextView upload_post;
    private RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;
    private List<Post> posts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        home_ava = view.findViewById(R.id.profile_home_ava);
        upload_post = view.findViewById(R.id.upload_user_post);
        home_ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, new ProfileFragment());
                fragmentTransaction.addToBackStack("profile");
                fragmentTransaction.commit();
            }
        });

        upload_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UploadPostActivity.class);
                startActivity(intent);
            }
        });

//        new feeds
//        recyclerview bs

        Log.i(TAG, "CHECK CRASHING");
        recyclerViewPosts = view.findViewById(R.id.recyclerView);
        recyclerViewPosts.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);// -> latest post put on top
        linearLayoutManager.setReverseLayout(true);
        recyclerViewPosts.setLayoutManager(linearLayoutManager);

        posts = new ArrayList<>();
        postAdapter = new PostAdapter(posts, getContext());
        recyclerViewPosts.setAdapter(postAdapter);

//        add things to new feed
        readPost();

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            requireActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    woowowowwwo adding posts to posts(list) to run on new feeds
    public void readPost(){
//        get database ref (get to posts)
        FirebaseDatabase.getInstance().getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                clear posts(list)
                posts.clear();
//                iterate through all posts in db
                for (DataSnapshot sp :snapshot.getChildren()){
//                    set each post key in db as post ojb and add to list
                    Post post = sp.getValue(Post.class);
                    post.setPost_id(sp.getKey());
                    Log.i(TAG, "ID: " + post.getPost_id());
                    posts.add(post);
                }
                postAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}