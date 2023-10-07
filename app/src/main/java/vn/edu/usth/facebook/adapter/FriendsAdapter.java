package vn.edu.usth.facebook.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.R;
import vn.edu.usth.facebook.fragment.OtherUserProfileFragment;
import vn.edu.usth.facebook.model.Users;

//TODO: function to add friend after click on accept
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private String TAG = "FRIENDS ADAPTER";
    private List<Users> friends_req;
    private Context context;

    //    firebase stuff
    private FirebaseUser current_user;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorage = FirebaseStorage.getInstance().getReference();


    public FriendsAdapter(List<Users> friends_req, Context context){
        this.friends_req = friends_req;
        this.context = context;
    }

    public void setData(List<Users> newFriendsList) {
        friends_req.clear();
        friends_req.addAll(newFriendsList);
        notifyDataSetChanged();
    }

    public List<Users> getFriends() {
        return friends_req;
    }

    public void removeFriend(int position) {
        friends_req.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_req, parent, false);
        return new FriendsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.ViewHolder holder, int position) {
        current_user = FirebaseAuth.getInstance().getCurrentUser();
        Users friend_req = friends_req.get(position);
        holder.friend_req_ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new OtherUserProfileFragment(friend_req.getUser_id());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.friend_fragment, myFragment).addToBackStack(null).commit();
            }
        });


//        get friends ava
        getUserImg(mStorage.child("users").child(friend_req.getUser_id()), holder);

        Log.i(TAG,"FRIEND REQ NAME CHECK" + friend_req.getFirst_name());

        getUser_name(friend_req.getUser_id(), holder);



        holder.accept_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.friend_accepted.setVisibility(View.VISIBLE);
                holder.accept_friend.setVisibility(View.GONE);
                holder.not_accept_friend.setVisibility(View.GONE);

                add_friend(friend_req);
            }
        });
        holder.not_accept_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                friends.remove(holder.getAdapterPosition());
//                notifyItemRemoved(holder.getAdapterPosition());
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    removeFriend(adapterPosition);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return friends_req.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView friend_req_ava;
        private TextView friend_req_name, req_date, mutual_friends, friend_accepted;
        private AppCompatButton accept_friend, not_accept_friend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            friend_req_ava = itemView.findViewById(R.id.ava_friend_req);
            friend_req_name = itemView.findViewById(R.id.friend_req_name);
            req_date = itemView.findViewById(R.id.time_request);
            mutual_friends = itemView.findViewById(R.id.mutual_friends);
            friend_accepted = itemView.findViewById(R.id.friend_accepted);
            accept_friend = itemView.findViewById(R.id.accept_friend);
            not_accept_friend = itemView.findViewById(R.id.not_accept_friend);
        }
    }

    public void getUserImg(StorageReference user_storage,@NonNull FriendsAdapter.ViewHolder holder){
//        todo: threading
        user_storage.child("avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.friend_req_ava);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Picasso.get().load(R.drawable.default_ava).into(holder.friend_req_ava);
//                Log.e(TAG,"GET AVA/BANNER IMG ERROR:" + e);
            }
        });
    }

    public void getUser_name(String friend_id, @NonNull FriendsAdapter.ViewHolder holder){
        mDatabase.child("users").child(friend_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.friend_req_name.setText(snapshot.child("first_name").getValue()+ " " + snapshot.child("sur_name").getValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void add_friend(Users friend_req){
//       adding friend -> current_user/friends list in db
        Log.i(TAG, "FRIEND DATABSE: " + mDatabase.child("users").child(current_user.getUid()).child("friends").child(friend_req.getUser_id()));
        mDatabase.child("users").child(current_user.getUid()).child("friends").child(friend_req.getUser_id()).setValue(true);
//       do the same way around
        mDatabase.child("users").child(friend_req.getUser_id()).child("friends").child(current_user.getUid()).setValue(true);
//        remove request in db
//        brain fuck: user A(current_user) send request -> save request in user B(friend)
//                    but now to accept request=> have to login to B(friend) whom is now current user
//                    ==>B(current_user) and A is friend => delete request -> current user(B)/friend_reqs/friend(A)
        mDatabase.child("users").child(current_user.getUid()).child("friend_reqs").child(friend_req.getUser_id()).removeValue();
    }
}