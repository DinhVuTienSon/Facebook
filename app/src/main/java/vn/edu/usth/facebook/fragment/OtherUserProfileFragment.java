package vn.edu.usth.facebook.fragment;

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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.EditProfileActivity;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.adapter.PostAdapter;
import vn.edu.usth.facebook.adapter.UserFriendsAdapter;
import vn.edu.usth.facebook.model.Post;
import vn.edu.usth.facebook.model.Users;

//TODO: function to call all user information
//TODO: function to display friend's ava, name
//TODO: function to display user's post

public class OtherUserProfileFragment extends Fragment {
    //    for bug fixes and error messages
    private String TAG = "OTHER PROFILE FRAGMENT";
    public Button addFriendBtn;

    private Toolbar toolbar;
    private ImageView background;
    private CircleImageView avatar;
    private TextView name, bio, live_in, hobby, work, education, contact;
    private AppCompatButton see_all_user_friends, see_less_user_friends;
    private boolean isExpanded = false;

    //    firebase stuff
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    private FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();

    private String uid;
    private ArrayList<Post> posts;
    private ArrayList<Users> userss;
    private List<Users> friends;
    private RecyclerView recyclerViewPosts;
    private RecyclerView recyclerViewFriends;
    private List<String> not_friends;
    private PostAdapter postAdapter;
    private UserFriendsAdapter friendsAdapter;

    private ProgressBar loadingIndicator;

    public OtherUserProfileFragment() {
        // Required empty public constructor
    }

    public OtherUserProfileFragment(String profile_id){
        this.uid = profile_id;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });
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
        View view = inflater.inflate(R.layout.fragment_other_user_profile, container, false);

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

//        isFriend(uid, addFriendBtn);

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

        recyclerViewFriends = view.findViewById(R.id.user_friends_recyclerView);
        recyclerViewFriends.setHasFixedSize(true);
        LinearLayoutManager user_friend_layoutManager = new LinearLayoutManager(getContext());
        user_friend_layoutManager.setStackFromEnd(true);
        user_friend_layoutManager.setReverseLayout(true);
        recyclerViewFriends.setLayoutManager(user_friend_layoutManager);

        friends = new ArrayList<>();
        friendsAdapter = new UserFriendsAdapter(friends ,getContext());
        recyclerViewFriends.setAdapter(friendsAdapter);

        getFriends();

        userss = new ArrayList<>();

        if (userss.size() <= 3) {
            see_less_user_friends.setVisibility(View.GONE);
        } else {
            see_all_user_friends.setVisibility(View.VISIBLE);
        }
        see_all_user_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpanded = true;
                friendsAdapter.setDataUserFriends(userss);
                see_all_user_friends.setVisibility(View.GONE);
                see_less_user_friends.setVisibility(View.VISIBLE);
            }
        });


        see_less_user_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpanded = false;
                friendsAdapter.setDataUserFriends(getLimitedUserFriends());
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
//todo: save this
        addFriendBtn = view.findViewById(R.id.btnAddFriend);
        addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addFriendBtn.getTag().equals("add")){
//                    add_friend_req(uid);
                }
                else if (addFriendBtn.getTag().equals("accept req")){
//                    add_friend(uid);
                }
                else if (addFriendBtn.getTag().equals("remove fr")){
//                    remove_friend(uid);
                }
                //TODO: add friend btn function
            }
        });
//        todo: hrere
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
        if (isExpanded || userss.size() <= 5) {
            return userss;
        } else {
            return new ArrayList<>(userss.subList(0, 5));
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
    private void getFriends(){
        mDatabase.child("users").child(uid).child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friends.clear();
                for(DataSnapshot sp : snapshot.getChildren()){
                    Users friend = snapshot.getValue(Users.class);
                    friend.setUser_id(sp.getKey());
                    friends.add(friend);
                }
                friendsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void isFriend(String profile_id, Button button){
        mDatabase.child("users").child(current_user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("friends").child(uid).exists()){
                    button.setText("already friend");
                    button.setTag("remove friend");
                }
                else{
                    if(snapshot.child("friend_reqs").child(uid).exists()){
                        button.setText("requested");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void add_friend_req(String profile_id){
//       adding current_user_id -> friend_id/friends_req/ list in db
//        Log.i(TAG, "FRIEND DATABSE: " + mDatabase.child("users").child(friend_recc.getUser_id()).child("friend_reqs").child(current_user.getUid()));
        mDatabase.child("users").child(profile_id).child("friend_reqs").child(current_user.getUid()).setValue(true);
    }

    public void add_friend(String friend_req){
//       adding friend -> current_user/friends list in db
        Log.i(TAG, "FRIEND DATABSE: " + mDatabase.child("users").child(current_user.getUid()).child("friends").child(friend_req));
        mDatabase.child("users").child(current_user.getUid()).child("friends").child(friend_req).setValue(true);
//       do the same way around
        mDatabase.child("users").child(friend_req).child("friends").child(current_user.getUid()).setValue(true);
//        remove request in db
//        brain fuck: user A(current_user) send request -> save request in user B(friend)
//                    but now to accept request=> have to login to B(friend) whom is now current user
//                    ==>B(current_user) and A is friend => delete request -> current user(B)/friend_reqs/friend(A)
        mDatabase.child("users").child(current_user.getUid()).child("friend_reqs").child(friend_req).removeValue();
    }

    public void remove_friend(String profile_id){
        mDatabase.child("users").child(current_user.getUid()).child("friends").child(profile_id).removeValue();
//       do the same way around
        mDatabase.child("users").child(profile_id).child("friends").child(current_user.getUid()).removeValue();
    }
}