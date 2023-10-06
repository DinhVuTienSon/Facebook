package vn.edu.usth.facebook.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.CommentActivity;
import vn.edu.usth.facebook.EditProfileActivity;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.fragment.HomeFragment;
import vn.edu.usth.facebook.fragment.ProfileFragment;
import vn.edu.usth.facebook.model.Post;
import vn.edu.usth.facebook.model.Users;

//TODO: function to count the number of likes, comments
//TODO: function to check if the post is liked or not -> call that function in post_like's onclick

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private String TAG = "POSTADAPTER";
    private List<Post> posts;
    private Context context;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private FirebaseUser user;

    public PostAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }


    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        user = FirebaseAuth.getInstance().getCurrentUser();

//        get firebase stuff
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();

        Post post = posts.get(position);
//        Log.i(TAG, "ACTUAL ID: " + getActual_post_id(post.getPost_id()));

//        get post's author
        post.getAuthor_ID_from_db();

        //TODO: add function in Post model to convert this to actual date
//        Log.i(TAG,"TESTING SETUP DATE" + post.getActual_date());
//        Log.i(TAG,"TESTING SETUP NEW DATE" + post.date_status());

        holder.time_post.setText(post.date_status());
        holder.post_description.setText(post.getPost_description());

//        get post image
        getPostImg(mStorage, holder, post);
//        Log.i(TAG, "ACTUAL ID: " + getActual_post_id(post.getPost_id()));

//        holder.post_likes.setText(post.getPostLikes());
//        holder.post_comments.setText(post.getPostComments());

//        get firebase stuff
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        DatabaseReference user_database = mDatabase.child("users").child(post.getAuthor_id());
        StorageReference user_storage = mStorage.child("users").child(post.getAuthor_id());

        Log.i(TAG, "STORAGE" + user_storage);
        user_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                getUserImg(user_storage, holder);
                holder.author_name.setText(user.getFirst_name() + " " + user.getSur_name());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        isLiked(post.getPost_id(), holder.post_likes);
//        getPostImg(holder, mStorage);

        holder.post_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.post_likes.getTag().equals("Like")){
                    writeNewPostLike(post, post.toLikesMap(user.getUid()),  mDatabase);
                    FirebaseDatabase.getInstance().getReference().child("post_likes").child(post.getPost_id()).child(user.getUid()).setValue(true);
                }
                else {
                    FirebaseDatabase.getInstance().getReference().child("post_likes").child(post.getPost_id()).child(user.getUid()).removeValue();
                }
            }
        });

        holder.post_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CommentActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        holder.author_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new ProfileFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, myFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView author_img;
        private TextView author_name, time_post, post_description;
        private ImageView post_img, post_likes;
        private TextView post_comments;
        private LinearLayout post_share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            author_img = itemView.findViewById(R.id.idCVAuthor);
            author_name = itemView.findViewById(R.id.idTVAuthorName);
            time_post = itemView.findViewById(R.id.idTVTime);
            post_description = itemView.findViewById(R.id.idTVDescription);
            post_img = itemView.findViewById(R.id.idIVPost);
            post_likes = itemView.findViewById(R.id.post_likes);
            post_comments = itemView.findViewById(R.id.idTVComments);
            post_share = itemView.findViewById(R.id.idLLShare);
        }
    }

    public void getUserImg(StorageReference user_storage,@NonNull PostAdapter.ViewHolder holder){
//        todo: threading
        user_storage.child("avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.author_img);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"GET AVA/BANNER IMG ERROR:" + e);
            }
        });
    }
//    Picasso.get().load(post.getPostImage()).into(holder.post_img);
//    get post img
    public void getPostImg(StorageReference mStorage,@NonNull PostAdapter.ViewHolder holder, Post post){
    //        todo: threading
//        need to get actual post id because post id in storage is
        mStorage.child("posts").child(post.getActual_post_id()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.post_img);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"GET POST IMG ERROR:" + e);
            }
        });
    }
    public void isLiked(String postId, ImageView imageView){
        FirebaseDatabase.getInstance().getReference().child("post_likes").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(user.getUid()).exists()){
                    imageView.setImageResource(R.drawable.liked_icon);
                    imageView.setTag("Liked");
                }
                else {
                    imageView.setImageResource(R.drawable.like_icon);
                    imageView.setTag("Like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void writeNewPostLike(Post post, Map<String,Object> post_like, DatabaseReference db){
        try{
//            add user_info map to db
            db.child("posts").child(post.getPost_id()).child("post_likes").updateChildren(post_like);
//          create pop up message when saved
//            Toast.makeText(EditProfileActivity.this, type + " updated", Toast.LENGTH_SHORT).show();
        }catch (Exception e) {
            Log.e(TAG, "ERROR: " + e);

        }

    }
}