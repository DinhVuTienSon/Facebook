package vn.edu.usth.facebook;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.model.Post;


public class HomeFragment extends Fragment {
    public CircleImageView home_ava;
    private RecyclerView recyclerView;
    private ArrayList<Post> posts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        home_ava = view.findViewById(R.id.profile_home_ava);
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

        posts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String authorName = "ST";
            String authorImage = "https://picsum.photos/600/300?random&"+i;
            String postDate = "now";
            String postDescription = "testing";
            String postImage = "https://picsum.photos/600/300?random&"+i;
            String postLikes = "2";
            String postComments = "3";

            Post post = new Post(authorImage, authorName, postDate, postDescription, postImage, postLikes, postComments);
            posts.add(post);}
            PostAdapter adapter = new PostAdapter(posts, HomeFragment.this);
            RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        return view;
    }
}