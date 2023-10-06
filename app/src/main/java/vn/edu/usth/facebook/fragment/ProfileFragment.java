package vn.edu.usth.facebook.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.EditProfileActivity;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.adapter.PostAdapter;
import vn.edu.usth.facebook.adapter.UserFriendsAdapter;
import vn.edu.usth.facebook.adapter.UserProfileAdapter;
import vn.edu.usth.facebook.model.Post;
import vn.edu.usth.facebook.model.Users;


//TODO: function to display friend's ava, name


public class ProfileFragment extends Fragment {
    //    for bug fixes and error messages
    private String TAG = "PROFILE FRAGMENT";
    public Button editBtn;

    private Toolbar toolbar;
    private ImageView background;
    private CircleImageView avatar;
    private TextView name, bio, live_in, hobby, work, education, contact;
    private AppCompatButton see_all_user_friends, see_less_user_friends;
    private boolean isExpanded = false;

    //    firebase stuff
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private FirebaseUser firebaseUser;

    private String uid;
    private ArrayList<Post> posts;
    private ArrayList<Users> users;
    private RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;

    private ProgressBar loadingIndicator;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//  get database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

//  get current user ID
        FirebaseUser c_user = mAuth.getCurrentUser();
        uid = c_user.getUid();

        mStorage = FirebaseStorage.getInstance().getReference();

//  check if user is logged in
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG,"onAuthStateChanged: sign_in: " + uid);
                }
                else{
                    Log.d(TAG, "signed out" + uid);
                }
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }

    //    when app start, check if user is still logged in
    @Override
    public void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    public void user_info(){
//        getting user storage ref
        StorageReference user_storage = mStorage.child("users/" + uid + "/");

        mDatabase.child("users").child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                name.setText(users.getFirst_name() + " " + users.getSur_name());
                bio.setText(users.getUser_bio());
//                get ava and background
                getUserImg(user_storage, "avatar");
                getUserImg(user_storage,"background");
                live_in.setText(users.getUser_live_in());
                work.setText(users.getUser_work());
                education.setText(users.getUser_education());
                hobby.setText(users.getUser_hobbies());
                contact.setText(users.getUser_links());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        String data = getContext().getSharedPreferences("PROFILE", Context.MODE_PRIVATE).getString("profileId", "none");
//        if(data.equals("none")){
//            profileId = firebaseUser.getUid();
//        }
//        else {
//            profileId = data;
//            getContext().getSharedPreferences("PROFILE", Context.MODE_PRIVATE).edit().clear().apply();
//        }

        toolbar = view.findViewById(R.id.profile_toolbar);

        background = view.findViewById(R.id.background);
        avatar = view.findViewById(R.id.avatar);
        name = view.findViewById(R.id.name);
        bio = view.findViewById(R.id.quote);
        live_in = view.findViewById(R.id.live_in);
        hobby = view.findViewById(R.id.hobby);
        work = view.findViewById(R.id.work);
        education = view.findViewById(R.id.education);
        contact = view.findViewById(R.id.contact);
        see_all_user_friends = view.findViewById(R.id.see_all_user_friends);
        see_less_user_friends = view.findViewById(R.id.see_less_user_friends);

        user_info();

        recyclerViewPosts = view.findViewById(R.id.user_post_recyclerView);
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

//        posts = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            String author_name = "ST";
//            String author_image = "https://picsum.photos/600/300?random&"+i;
//            String post_date = "now";
//            String post_description = "testing";
//            String post_image = "https://picsum.photos/600/300?random&"+i;
//            String post_likes = "2";
//            String post_comments = "3";
//
//            Post post = new Post(author_image, author_name, post_date, post_description, post_image, post_likes, post_comments);
//            posts.add(post);}
//        UserProfileAdapter adapter = new UserProfileAdapter(posts, ProfileFragment.this);
//        RecyclerView recyclerView = view.findViewById(R.id.user_post_recyclerView);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);

        users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String user_friend_name = "ST";
            String user_friend_ava = "https://picsum.photos/600/300?random&"+i;

            Users user = new Users(user_friend_ava, user_friend_name,"","");
            users.add(user);}

        UserFriendsAdapter userFriendsAdapter = new UserFriendsAdapter(getLimitedUserFriends(), getContext());
        RecyclerView user_friends_recyclerView = view.findViewById(R.id.user_friends_recyclerView);

        LinearLayoutManager user_friend_layoutManager = new LinearLayoutManager(getContext());
        user_friends_recyclerView.setLayoutManager(user_friend_layoutManager);
        user_friends_recyclerView.setAdapter(userFriendsAdapter);

        if (users.size() <= 3) {
            see_less_user_friends.setVisibility(View.GONE);
        } else {
            see_all_user_friends.setVisibility(View.VISIBLE);
        }
        see_all_user_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpanded = true;
                userFriendsAdapter.setDataUserFriends(users);
                see_all_user_friends.setVisibility(View.GONE);
                see_less_user_friends.setVisibility(View.VISIBLE);
            }
        });

        see_less_user_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpanded = false;
                userFriendsAdapter.setDataUserFriends(getLimitedUserFriends());
                see_all_user_friends.setVisibility(View.VISIBLE);
                see_less_user_friends.setVisibility(View.GONE);
            }
        });

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        activity.getSupportActionBar().setTitle("");


//  add user infos from database to fragment
//        TODO: check for initial delay when updating name


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });

        editBtn = view.findViewById(R.id.btnEdit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void getUserImg(StorageReference user_storage, String type){
//        todo: threading
        user_storage.child(type).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (type == "avatar") {
                    Picasso.get().load(uri).into(avatar);
                }
                else{
                    Picasso.get().load(uri).into(background);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"GET AVA/BANNER IMG ERROR:" + e);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.fb_menu) {
            requireActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private ArrayList<Users> getLimitedUserFriends() {
        if (isExpanded || users.size() <= 5) {
            return users;
        } else {
            return new ArrayList<>(users.subList(0, 5));
        }
    }
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
                    post.getAuthor_ID_from_db();
                    if (post.getAuthor_id().equals(uid)){
                        Log.i(TAG, "ID: " + post.getPost_id());
                        posts.add(post);
                    }
//                    post.setAuthor_id(FirebaseAuth.getInstance().getCurrentUser().toString());
                }
                postAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}