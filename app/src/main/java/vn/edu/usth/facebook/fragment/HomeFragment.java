package vn.edu.usth.facebook.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.EditProfileActivity;
import vn.edu.usth.facebook.UploadPostActivity;
import vn.edu.usth.facebook.adapter.PostAdapter;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.model.Post;


public class HomeFragment extends Fragment {
    public CircleImageView home_ava;
    public TextView upload_post;
    private RecyclerView recyclerView;
    private ArrayList<Post> posts;

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

        posts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String author_name = "ST";
            String author_image = "https://picsum.photos/600/300?random&"+i;
            String post_date = "now";
            String post_description = "testing";
            String post_image = "https://picsum.photos/600/300?random&"+i;
            String post_likes = "2";
            String post_comments = "3";

            Post post = new Post(author_image, author_name, post_date, post_description, post_image, post_likes, post_comments);
            posts.add(post);}
            PostAdapter adapter = new PostAdapter(posts, HomeFragment.this);
            RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

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
}