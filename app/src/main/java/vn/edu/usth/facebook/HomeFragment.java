package vn.edu.usth.facebook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.edu.usth.facebook.model.Post;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Post> posts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        posts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String authorName = "ST";
            String authorImage = "https://picsum.photos/600/300?random&";
            String postDate = "now";
            String postDescription = "testing";
            String postImage = "https://picsum.photos/600/300?random&";
            String postLikes = "2";
            String postComments = "3";

            Post post = new Post(authorImage, authorName, postDate, postDescription, postImage, postLikes, postComments);
            posts.add(post);
            PostAdapter adapter = new PostAdapter(posts, HomeFragment.this);
            RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
}