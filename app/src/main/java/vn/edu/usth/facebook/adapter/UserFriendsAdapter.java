package vn.edu.usth.facebook.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.fragment.ProfileFragment;
import vn.edu.usth.facebook.model.Users;


public class UserFriendsAdapter extends RecyclerView.Adapter<UserFriendsAdapter.ViewHolder>{
    private String TAG = "USER FRIENDS ADAPTER";
    private Context context;
    private List<Users> friends;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    private FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
    public UserFriendsAdapter(List<Users> friends, Context context){
        this.friends = friends;
        this.context = context;
    }

    public void setDataUserFriends(List<Users> newUsersFriendsList) {
        friends.clear();
        friends.addAll(newUsersFriendsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserFriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_friends_container, parent, false);
        return new UserFriendsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFriendsAdapter.ViewHolder holder, int position) {


        Users friend = friends.get(position);
        Log.i(TAG, "FRIEND ID: " + friend.getUser_id());
        getUserImg(mStorage.child("users").child(friend.getUser_id()), holder);
        getUser_name(friend.getUser_id(), holder);

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ava_user_friend;
        private TextView user_friend_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ava_user_friend = itemView.findViewById(R.id.ava_user_friend);
            user_friend_name = itemView.findViewById(R.id.user_friend_name);
        }
    }

    public void getUserImg(StorageReference user_storage, @NonNull UserFriendsAdapter.ViewHolder holder){
//        todo: threading
        user_storage.child("avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.ava_user_friend);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Picasso.get().load(R.drawable.default_ava).into(holder.ava_user_friend);
                Log.e(TAG,"GET AVA/BANNER IMG ERROR: SKIPPP");
            }
        });
    }

    public void getUser_name(String friend_id, @NonNull UserFriendsAdapter.ViewHolder holder){
        mDatabase.child("users").child(friend_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.user_friend_name.setText(snapshot.child("first_name").getValue()+ " " + snapshot.child("sur_name").getValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}